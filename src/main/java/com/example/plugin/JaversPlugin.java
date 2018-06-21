package com.example.plugin;

import org.zeroturnaround.javarebel.ClassResourceSource;
import org.zeroturnaround.javarebel.Integration;
import org.zeroturnaround.javarebel.IntegrationFactory;
import org.zeroturnaround.javarebel.Plugin;

public class JaversPlugin implements Plugin {

  @Override
  public void preinit() {
    Integration integration = IntegrationFactory.getInstance();
    ClassLoader cl = getClass().getClassLoader();

    integration.addIntegrationProcessor(cl,
        "org.javers.common.reflection.ReflectionUtil",
        new ReflectionUtilCBP());
  }

  @Override
  public boolean checkDependencies(ClassLoader cl, ClassResourceSource crs) {
    // check if plugin should be enabled in classloader cl
    return crs.getClassResource("org.javers.core.Javers") != null;
  }

  @Override
  public String getId() {
    return "javers_plugin";
  }

  @Override
  public String getName() {
    return "Javers plugin";
  }

  @Override
  public String getDescription() {
    return "Plugin to work around the non-determinicity bug with reflective class instantiation within Javers framework";
  }

  @Override
  public String getAuthor() {
    return "Tiit Oja";
  }

  @Override
  public String getWebsite() {
    return "zeroturnaround.com";
  }

  @Override
  public String getSupportedVersions() {
    return null;
  }

  @Override
  public String getTestedVersions() {
    return null;
  }
}
