package com.lostin.auth.jwt.jwk.manager;


import com.fasterxml.jackson.annotation.*;
import com.lostin.auth.exception.ServerError;
import com.lostin.auth.jwt.jwk.core.NimbusRsaJwk;
import com.lostin.auth.jwt.jwk.core.RsaJwk;
import com.lostin.auth.jwt.jwk.gen.NimbusRSAKeyGenerator;
import com.nimbusds.jose.jwk.RSAKey;
import jakarta.annotation.PostConstruct;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/*
Key manager that stores key in the file at the project root.
Might be deprecated in future when using vaults or other types of secure storage for keys.
 */
@Slf4j
@Component
@Qualifier("nimbus-impl-local-storage")
@Profile("dev")
public class TempNimbusRsaJWKeyManager implements RsaJWKeyManager {

    private final String fileName;
    private final NimbusRSAKeyGenerator rsaKeyGenerator;
    private final ObjectMapper objectMapper;

    private Map<String, NimbusRsaJwk> inactiveKeysMap;
    private NimbusRsaJwk activeKey;

    public TempNimbusRsaJWKeyManager(
            @Value("${AUTH_RSA_KEYS_LOCAL_STORAGE_PATH}")
            String fileName,
            NimbusRSAKeyGenerator rsaKeyGenerator,
            ObjectMapper objectMapper
    ) {
        this.fileName = fileName;
        this.rsaKeyGenerator = rsaKeyGenerator;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        // Initializes keys from a file; throws on failure
        if(fileName == null || fileName.isBlank()){
            log.error("JWK Manager Initialization Error: File name is not set");
            throw new ServerError();
        }

        // todo: write validation logic that won't deserialize file if file doesn't exist

        try {
            KeysFile keysFile = this.objectMapper.readValue(new File(fileName), KeysFile.class);
            this.inactiveKeysMap = keysFile.getAllDeprecatedKeysMap();
            this.activeKey = keysFile.getActiveKey();
            log.info("JWK Manager Initialized!");
        }catch (JacksonException e) {
            log.error("Jwk Manager Initialization Error", e);
            throw new ServerError();
        } catch (Exception e) {
            log.error("Error reading keys file, Probably the file is broken", e);
            throw new ServerError();
        }

        if(this.activeKey == null){
            if(inactiveKeysMap.isEmpty()) {
                log.warn("There are no keys in the file, please generate new one!");
            }else
                log.warn("There is no active keys, all inactive, please generate new one!");
        }
        if(inactiveKeysMap==null)
            this.inactiveKeysMap = new HashMap<>();
    }

    @Override
    public void saveKeys() {
        log.info("Saving keys into file: {}", fileName);
        KeysFile keysFile = new KeysFile();
        keysFile.setActiveKeys(this.activeKey);
        keysFile.setDeprecatedKeys(this.inactiveKeysMap);
        this.objectMapper.writeValue(new File(this.fileName), keysFile);
    }

    @Override
    public Optional<RsaJwk> getActiveKey() {
        if(activeKey == null){
            log.warn("There is no active keys, please generate new one!");
            return Optional.empty();
        }
        return Optional.of(activeKey);
    }

    @Override
    public Optional<RsaJwk> getKeyById(String id) {
        if (this.getActiveKey().isPresent() && activeKey.getKeyId().equals(id)) {
            return Optional.of(activeKey);
        }
        if (inactiveKeysMap.containsKey(id)) {
            return Optional.of(inactiveKeysMap.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<RsaJwk> getAllKeys() {
        List<RsaJwk> allKeys = new ArrayList<>(inactiveKeysMap.values().stream().toList());
        // adds active keys to the list if there is one
        this.getActiveKey().ifPresent(allKeys::add);
        return allKeys;
    }

    @Override
    public RsaJwk generateNewKeyWithCustomId(String keyId) {
        if(!isKeyIdValid(keyId) || isKeyIdTaken(keyId)){
            log.error("Key id is invalid or already taken: {}",keyId);
            throw new ServerError();
        }

        return genNewKeyWithValidId(keyId);
    }

    @Override
    public RsaJwk generateNewKey() {
        String keyId = generateNewKeyId();
        return genNewKeyWithValidId(keyId);
    }

    private boolean isKeyIdTaken(String keyId) {
        return inactiveKeysMap.containsKey(keyId) || (activeKey !=null && activeKey.getKeyId().equals(keyId));
    }

    private boolean isKeyIdValid(String keyId) {
        return keyId != null && !keyId.isBlank();
    }

    // for internal use only if key uniqueness already checked
    private NimbusRsaJwk genNewKeyWithValidId(String keyId){
        try {
            NimbusRsaJwk newKey = rsaKeyGenerator.generateRSAKey(keyId);
            if(this.activeKey!=null){
                inactiveKeysMap.put(this.activeKey.getKeyId(),this.activeKey);
            }
            this.activeKey = newKey;
            return newKey;
        }catch (Exception e){
            log.error("Error generating new key",e);
            throw new ServerError();
        }
    }

    private String generateNewKeyId() {
        String keyId = Instant.now().toString();
        if(isKeyIdTaken(keyId)){
            return generateNewKeyId();
        }
        return keyId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KeysFile {

        KeyEntry active;
        List<KeyEntry> deprecated;

        protected NimbusRsaJwk getActiveKey(){
            if(this.active == null){
                return null;
            }
            return active.getKey();
        }

        protected Map<String, NimbusRsaJwk> getAllDeprecatedKeysMap() {
            if (this.deprecated == null) {
                return new HashMap<>();
            }
            return new HashMap<>(
                    this.deprecated.stream()
                            .collect(Collectors.toMap(
                                    KeyEntry::getId,
                                    KeyEntry::getKey
                            ))
            );
        }

        protected void setActiveKeys(NimbusRsaJwk activeKeys) {
            if(activeKeys == null) {
                this.active = null;
                return;
            }
            this.active = new KeyEntry(activeKeys.toJsonMap());
        }

        protected void setDeprecatedKeys(Map<String, NimbusRsaJwk> keys) {
            if (keys == null) {
                this.deprecated = new ArrayList<>();
                return;
            }
            this.deprecated = keys.values().stream().filter(Objects::nonNull).map(KeyEntry::from).toList();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class KeyEntry {

        @JsonIgnore
        private RSAKey nimbusRsaKey;
        public Map<String, Object> key;

        protected KeyEntry(Map<String, Object> keyMap) {
            this.key = keyMap;
        }

        protected static KeyEntry from(NimbusRsaJwk rsaJwk) {
            if(rsaJwk == null)
                return null;
            return new KeyEntry(rsaJwk.toJsonMap());
        }

        private void parse() {
            if (key == null) {
                log.error("Key map is null, please check your keys file");
                throw new ServerError();
            }
            try {
                this.nimbusRsaKey = RSAKey.parse(key);
            } catch (ParseException e) {
                log.error("Error parsing Nimbus RSA key from file", e);
                throw new ServerError();
            }
        }

        protected NimbusRsaJwk getKey() {
            if (nimbusRsaKey == null) {
                parse();
            }
            return new NimbusRsaJwk(nimbusRsaKey);
        }

        protected String getId() {
            if (nimbusRsaKey == null) {
                parse();
            }
            return nimbusRsaKey.getKeyID();
        }
    }
}
