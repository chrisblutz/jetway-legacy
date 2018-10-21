# Jetway

Jetway is an in-development Java library that provides a layer of abstraction above the FAA's monthly NASR subscription files.
It uses Apache's XMLBeans and a compiled AIXM 5.1 schema to load the NASR data and convert it into a custom data type that allows
for simpler access to information.

## Third-Party Libraries

#### Apache XMLBeans ([xmlbeans.apache.org](https://xmlbeans.apache.org/))

Jetway uses XMLBeans to load AIXM-formatted XML data.  XMLBeans is licensed under the
[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

#### Apache Log4j 2 ([logging.apache.org/log4j](https://logging.apache.org/log4j/2.x/))

Jetway uses Log4j to log its internal processes and provide context to errors that might arise.  Log4j 2 is licensed under the
[Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0).

#### AIXM 5.1 XML Schema ([aixm.aero](http://aixm.aero/))

Jetway uses the AIXM 5.1 schema alongside XMLBeans to load the FAA's NASR files.  AIXM is a joint development of the US Federal
Aviation Administration, the US National Geospatial Intelligence Agency, and the European Organization for the Safety of Air Navigation.
The specific schema compiled and used by Jetway is distributed with each FAA NASR zip file.

#### FAA Extensions to AIXM 5.1

Jetway uses the FAA's extensions to the AIXM 5.1 schema to load certain XML elements specific to the FAA's NASR files.

## Copyright

Jetway is licensed under the Apache License, Version 2.0.  See [LICENSE](LICENSE) for more information.
