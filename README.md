# QATCH

This is a fork and re-envisioning of the QATCH Quality Modeling Framework found
at [https://github.com/AuthEceSoftEng/qatch](AuthEceSoftEng/qatch)

The goal of this new project is to provide a means to evaluate the quality of a software project.

### The Modules

This project has been refactored into multiple modules:

* Core ([git](https://github.com/empirilytics/qatch/core))
* App ([git](https://github.com/empirilytics/qatch/app))
  * CLI ([git](https://github.com/empirilytics/qatch/app/cli))
  * GUI ([git](https://github.com/empirilytics/qatch/app/gui))
* Anlyzers ([git](https://github.com/empirilytics/qatch/analyzers))
* Calibration ([git](https://github.com/empirilytics/qatch/calibration))
* MicroService ([git](https://github.com/empirilytics/qatch/microservice))
* ActiveJDBC Gradle Plugin ([git](https://github.com/empirilytics/qatch/activejdbc))
* buildSrc ([git](https://github.com/empirilytics/qatch/buildSrc))

Others (not currently in development):

* IntegrationTesting ([git](https://github.com/empirilytics/qatch/it))
* WebApp ([git](https://github.com/empirilytics/qatch/webapp))
* WebService(old) ([git](https://github.com/empirilytics/qatch/webservice_old))

### License

The original code is copyright (c) 2017 under the MIT License to ISSEL Soft Eng Team, while all new code is copyright (
c) 2022 under the MIT License to Empirilytics.

### Contributing

See the individual modules for more information on contributing

### Authors and Acknowledgement

* **Isaac D. Griffith, Ph.D.** - Drastically reformatted the project into multiple modules and refactored several key
  areas for performance efficiency, maintainability, style, etc. However more work must be done. Is in the process of
  expanding the capabilities of this approach to be aligned with the needs of industry.
* **Miltiadis G. Siavvas, Ph.D.** - Developed the QATCH approach during their dissertation work. However, has not worked
  on it since
