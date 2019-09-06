@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  ids_kafka_simple_producer_consumer startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and IDS_KAFKA_SIMPLE_PRODUCER_CONSUMER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args
if "%@eval[2+2]" == "4" goto 4NT_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*
goto execute

:4NT_args
@rem Get arguments from the 4NT Shell from JP Software
set CMD_LINE_ARGS=%$

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\ids_kafka_simple_producer_consumer-1.0.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\commons-lang3-3.4.jar;%APP_HOME%\lib\guava-18.0.jar;%APP_HOME%\lib\spring-jdbc-4.2.1.RELEASE.jar;%APP_HOME%\lib\spring-orm-4.2.1.RELEASE.jar;%APP_HOME%\lib\spring-oxm-4.2.1.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-actuator-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-web-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-aop-1.4.0.RELEASE.jar;%APP_HOME%\lib\jackson-core-2.8.5.jar;%APP_HOME%\lib\jackson-annotations-2.8.5.jar;%APP_HOME%\lib\jackson-databind-2.8.5.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.8.5.jar;%APP_HOME%\lib\jackson-datatype-joda-2.8.5.jar;%APP_HOME%\lib\logback-core-1.0.13.jar;%APP_HOME%\lib\logback-classic-1.0.13.jar;%APP_HOME%\lib\logback-access-1.0.13.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.5.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.5.jar;%APP_HOME%\lib\metrics-core-3.1.0.jar;%APP_HOME%\lib\metrics-servlets-3.1.0.jar;%APP_HOME%\lib\slf4j-api-1.7.7.jar;%APP_HOME%\lib\findbugs-annotations-1.3.9-1.jar;%APP_HOME%\lib\httpclient-4.4.jar;%APP_HOME%\lib\aspectjrt-1.8.4.jar;%APP_HOME%\lib\jolokia-core-1.3.3.jar;%APP_HOME%\lib\kafka-clients-2.2.0.jar;%APP_HOME%\lib\monitoring-interceptors-5.2.1.jar;%APP_HOME%\lib\kafka-avro-serializer-5.2.1.jar;%APP_HOME%\lib\kafka-streams-avro-serde-4.1.0.jar;%APP_HOME%\lib\aspectjweaver-1.8.4.jar;%APP_HOME%\lib\spring-beans-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-core-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-tx-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-actuator-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-tomcat-1.4.0.RELEASE.jar;%APP_HOME%\lib\hibernate-validator-5.2.4.Final.jar;%APP_HOME%\lib\spring-web-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-webmvc-4.3.2.RELEASE.jar;%APP_HOME%\lib\spring-aop-4.3.2.RELEASE.jar;%APP_HOME%\lib\joda-time-2.9.4.jar;%APP_HOME%\lib\metrics-healthchecks-3.1.0.jar;%APP_HOME%\lib\metrics-json-3.1.0.jar;%APP_HOME%\lib\metrics-jvm-3.1.0.jar;%APP_HOME%\lib\httpcore-4.4.5.jar;%APP_HOME%\lib\commons-codec-1.10.jar;%APP_HOME%\lib\json-simple-1.1.1.jar;%APP_HOME%\lib\zstd-jni-1.3.8-1.jar;%APP_HOME%\lib\lz4-java-1.5.0.jar;%APP_HOME%\lib\snappy-java-1.1.7.2.jar;%APP_HOME%\lib\common-utils-5.2.1.jar;%APP_HOME%\lib\avro-1.8.1.jar;%APP_HOME%\lib\kafka-schema-registry-client-5.2.1.jar;%APP_HOME%\lib\common-config-5.2.1.jar;%APP_HOME%\lib\spring-boot-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-autoconfigure-1.4.0.RELEASE.jar;%APP_HOME%\lib\spring-boot-starter-logging-1.4.0.RELEASE.jar;%APP_HOME%\lib\snakeyaml-1.17.jar;%APP_HOME%\lib\spring-context-4.3.2.RELEASE.jar;%APP_HOME%\lib\tomcat-embed-core-8.5.4.jar;%APP_HOME%\lib\tomcat-embed-el-8.5.4.jar;%APP_HOME%\lib\tomcat-embed-websocket-8.5.4.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\jboss-logging-3.3.0.Final.jar;%APP_HOME%\lib\classmate-1.3.1.jar;%APP_HOME%\lib\spring-expression-4.3.2.RELEASE.jar;%APP_HOME%\lib\jackson-core-asl-1.9.13.jar;%APP_HOME%\lib\jackson-mapper-asl-1.9.13.jar;%APP_HOME%\lib\paranamer-2.7.jar;%APP_HOME%\lib\commons-compress-1.8.1.jar;%APP_HOME%\lib\xz-1.5.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.21.jar;%APP_HOME%\lib\zookeeper-3.4.13.jar;%APP_HOME%\lib\zkclient-0.10.jar;%APP_HOME%\lib\jline-0.9.94.jar;%APP_HOME%\lib\audience-annotations-0.5.0.jar;%APP_HOME%\lib\netty-3.10.6.Final.jar

@rem Execute ids_kafka_simple_producer_consumer
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %IDS_KAFKA_SIMPLE_PRODUCER_CONSUMER_OPTS%  -classpath "%CLASSPATH%" com.adobe.ids.CanaryApplication %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable IDS_KAFKA_SIMPLE_PRODUCER_CONSUMER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%IDS_KAFKA_SIMPLE_PRODUCER_CONSUMER_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
