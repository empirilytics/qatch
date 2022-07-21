# buildSrc Module

This module contains the module build templates.

### Usage

All modules of this project can utilize these build templates to reduce redundancy across module `build.gradle` files

Currently, there are five templates:

* `java-conventions` This template is the base for java and contains the common dependencies and plugins needed across
  all modules
* `java-app-conventions` This template is the base for java modules that generate an executable application (CLI or GUI)
    * Extends `java-conventions`
* `java-library-conventions` This template is the base for non application modules that will be use solely as a library
  for other modules.
    * Extends `java-conventions`
* `groovy-conventions` This template is the base for groovy modules and contains the common dependencies needed for such
  modules.
    * Extends `java-conventions` and thus can be used as the base for a project utilizing both `java` and `groovy`
* `groovy-app-conventions` This template is the base for groovy modules that generate an executable application
    * Extends both `java-app-conventions` and `groovy-conventions`

Review each of the templates to determine which is best for your situation. You can then use a template in your build as follows:

```groovy
plugins {
  id '<template-name>'
}
```

One last caveat: If you are constructing a new template, or modifying an existing one, by adding a new plugin. The following must be adhered to:

1. You add the plugin to the module as you normally would, however, if the module requires a version number, you must leave that out.
2. In the `build.gradle` file for the `buildSrc` module, you need to add a dependency to the correct artifact for the plugin
   * This is where you will specify the version number.

### Contributing

If you notice that a library or setting is general across multiple modules you should add it to either an existing
template, or derive a new one. Any new template should be added to the `src/main/groovy` source root named as
follows: `<template-name>.groovy`

### Authors and Acknowledgement

* Isaac D. Griffith, Ph.D. - Constructed each of the current templates