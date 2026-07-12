#!/usr/bin/env bash

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a symlink
PRG="$0"
# Need this for relative symlinks.
while [ -h "$PRG" ] ; do
    ls -ld "$PRG"
    link=`expr "$PRG" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done
SAVED="$(cd "$(dirname \"$PRG\")" && pwd)"
cd "$SAVED" || exit
APP_HOME="${APP_HOME:-$(pwd)}"

APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`

# Add default JVM options here
DEFAULT_JVM_OPTS=''-Xmx64m'' ''-Xms64m''

# Use the maximum available, or set MAX_FD != unlimited.
MAX_FD="maximum"

warn ( ) {
    echo "$*"
}

die ( ) {
    echo
    echo "$*"
    exit 1
}

# OS specific support (must be 'true' or 'false').
CYGWIN=false
MSYS=false
CYGWIN_NT=false
IS_MSYS=false
case \"$(uname)\" in
  CYGWIN* )
    CYGWIN=true
    ;;
  Darwin* )
    ;;
  MSYS* )
    MSYS=true
    IS_MSYS=true
    ;;
  MINGW* )
    MSYS=true
    IS_MSYS=true
    ;;
esac

if [ -z \"$JAVA_HOME\" ] ; then
    if [ -r /proc/version ] ; then
        if grep -q microsoft /proc/version ; then
            # running on WSL
            JAVA_HOME=$(dirname $(readlink -f $(which javac)))
            JAVA_HOME=${JAVA_HOME%/bin}
        fi
    fi
fi

if [ -z \"$JAVA_HOME\" ] ; then
    if [ -x /usr/libexec/java_home ]; then
        JAVA_HOME=`/usr/libexec/java_home`; export JAVA_HOME
    elif [ -d /usr/local/java -a -x /usr/local/java/bin/java ]; then
        JAVA_HOME=/usr/local/java
        export JAVA_HOME
    elif [ -d /usr/java -a -x /usr/java/bin/java ]; then
        JAVA_HOME=/usr/java
        export JAVA_HOME
    fi
fi

if [ -z \"$JAVA_HOME\" ] ; then
    die \"ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation.\"
fi

# Increase the maximum file descriptors if we can.
if [ \"$cygwin\" = \"false\" -a \"$darwin\" = \"false\" -a \"$nonstop\" = \"false\" ] ; then
    MAX_FD_LIMIT=`ulimit -H -n`
    if [ $? -eq 0 ] ; then
        if [ \"$MAX_FD\" = \"maximum\" -o \"$MAX_FD\" = \"max\" ] ; then
            MAX_FD=\"$MAX_FD_LIMIT\"
        fi
        ulimit -n $MAX_FD
        if [ $? -ne 0 ] ; then
            warn \"Could not set maximum file descriptor limit: $MAX_FD\"
        fi
    else
        warn \"Could not query maximum file descriptor limit: $MAX_FD_LIMIT\"
    fi
fi

# For Darwin, add options to specify how the application appears in the dock
if $darwin; then
    DEFAULT_JVM_OPTS=\"$DEFAULT_JVM_OPTS '-Xdock:name=$APP_NAME' '-Xdock:icon=$APP_HOME/media/dock.icns'\"
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if [ \"$cygwin\" = \"true\" -o \"$is_msys\" = \"true\" ] ; then
    APP_HOME=`cygpath --path --mixed \"$APP_HOME\"`
    CP=`cygpath --path --mixed \"$CLASSPATH\"`
    JAVACMD=`cygpath --unix \"$JAVACMD\"`
fi

exec \"$JAVACMD\" $DEFAULT_JVM_OPTS -classpath \"$APP_HOME/gradle/wrapper/gradle-wrapper.jar\" org.gradle.wrapper.GradleWrapperMain \"$@\"
