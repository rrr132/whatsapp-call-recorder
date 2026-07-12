@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the \"License\");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an \"AS IS\" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if \"x%DEBUG%\" == \"x\" @echo off
@REM enable echoing by default is disabled
@setlocal

@REM set %HOME% to equivalent of $HOME
if \"x%HOMEPATH%\" NEQ \"\" (
    set \"HOME=%HOMEPATH%\"
)

@REM Execute Gradle
\"%JAVA_HOME%\\bin\\java.exe\" %DEFAULT_JVM_OPTS% -classpath \"%APP_HOME%\\gradle\\wrapper\\gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain %*
