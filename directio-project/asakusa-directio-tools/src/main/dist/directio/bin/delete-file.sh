#!/bin/sh
#
# Copyright 2011-2014 Asakusa Framework Team.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

usage() {
    cat 1>&2 <<EOF
Delete Direct I/O Files/Directories

Usage:
    $0 base-path [-r] resource-pattern [resource-pattern [..]]

Parameters:
    -r, -recursive
        also delete directories and their contents recursively.
    base-path
        base path of files/directories to be deleted.
        this is used for detecting direct datasource configuration.
    resource-pattern
        resource pattern of files/directories to be deleted.
        this must be relative paths from base-path.
EOF
}

import() {
    _SCRIPT="$1"
    if [ -e "$_SCRIPT" ]
    then
        . "$_SCRIPT"
    else
        echo "$_SCRIPT is not found" 1>&2
        exit 1
    fi
}

if [ "$1" = "-h" -o "$1" = "-help" ]
then
    usage
    exit
fi

if [ $# -lt 2 ]
then
    usage
    exit 1
fi

_DIO_ROOT="$(cd "$(dirname "$0")/.." ; pwd)"

import "$_DIO_ROOT/conf/env.sh"
import "$_DIO_ROOT/libexec/validate-env.sh"

# Move to home directory
cd

_DIO_TOOL_LAUNCHER="com.asakusafw.runtime.stage.ToolLauncher"
_DIO_PLUGIN_CONF="$ASAKUSA_HOME/core/conf/asakusa-resources.xml"
_DIO_RUNTIME_LIB="$ASAKUSA_HOME/core/lib/asakusa-runtime-all.jar"
_DIO_CLASS_NAME="com.asakusafw.directio.tools.DirectIoDelete"

import "$_DIO_ROOT/libexec/configure-libjars.sh"
import "$_DIO_ROOT/libexec/configure-hadoop-cmd.sh"

echo "Starting Delete Direct I/O Files:"
echo " Hadoop Command: $HADOOP_CMD"
echo "          Class: $_DIO_CLASS_NAME"
echo "      Libraries: $_DIO_LIBJARS"
echo "      Arguments: $*"

"$HADOOP_CMD" jar \
    "$_DIO_RUNTIME_LIB" \
    "$_DIO_TOOL_LAUNCHER" \
    "$_DIO_CLASS_NAME" \
    -conf "$_DIO_PLUGIN_CONF" \
    -libjars "$_DIO_LIBJARS" \
    "$@"

_DIO_RET=$?
if [ $_DIO_RET -ne 0 ]
then
    echo "Delete Direct I/O Files failed with exit code: $_DIO_RET" 1>&2
    echo "  Runtime Lib: $_DIO_RUNTIME_LIB"  1>&2
    echo "     Launcher: $_DIO_TOOL_LAUNCHER"  1>&2
    echo "        Class: $_DIO_CLASS_NAME" 1>&2
    echo "Configuration: -conf $_DIO_PLUGIN_CONF"  1>&2
    echo "    Libraries: -libjars $_DIO_LIBJARS"  1>&2
    echo "    Arguments: $*" 1>&2
    exit $_DIO_RET
fi
