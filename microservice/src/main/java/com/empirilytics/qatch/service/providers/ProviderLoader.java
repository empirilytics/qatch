package com.empirilytics.qatch.service.providers;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

public class ProviderLoader {

  private Map<String, LanguageProvider> providers;

  private ProviderLoader() {}

  private static final class InstanceHolder {
    private static final ProviderLoader INSTANCE = new ProviderLoader();
  }

  public static ProviderLoader instance() {
    ProviderLoader inst = InstanceHolder.INSTANCE;
    if (inst.providers == null) {
      inst.initProviders();
    }
    return inst;
  }

  public LanguageProvider getProvider(String language) {
    if (providers != null && providers.containsKey(language)) return providers.get(language);
    return null;
  }

  private void initProviders() {
    providers = Maps.newHashMap();
    Map<String, String> map = null;
    try (BufferedReader br =
        new BufferedReader(
            new InputStreamReader(
                Objects.requireNonNull(
                    ProviderLoader.class.getResourceAsStream("/providers.json"))))) {
      Gson gson = new Gson();
      Type type = new TypeToken<Map<String, String>>() {}.getType();
      map = gson.fromJson(br, type);
    } catch (Exception ex) {

    }

    if (map != null) {
      map.forEach(
          (key, value) -> {
            LanguageProvider provider;
            try {
              Class<?> cls = Class.forName(value);
              Constructor<?> m = cls.getConstructor();
              provider = (LanguageProvider) m.newInstance();
              providers.put(key, provider);
            } catch (ClassNotFoundException
                | NoSuchMethodException
                | InvocationTargetException
                | InstantiationException
                | IllegalAccessException e) {
              e.printStackTrace();
            }
          });
    }
  }
}
