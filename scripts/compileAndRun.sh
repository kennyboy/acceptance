#!/bin/bash
# requires java, javac.
# Copyright (C) 2012    Lasath Fernando         (@lasath.fernando)
# Copyright (C) 2012    Benjamin James Wright   (@ben.wright)
# Copyright (C) 2012    Damon Stacey            (@damon.stacey)

. compile

if [ `whoami` == "damon" ]; then
   reset
fi
 
java -ea framework.TestRunner

