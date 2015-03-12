# Introduction #

A detailed description of how to build the project.


# Details #

1) Check out the code in Eclipse using the subclipse plugin from the following url:

svn checkout http://gsuspacesim.googlecode.com/svn/trunk/ gsuspacesim-read-only

2) Select the option to create a new java project.  Call it "spacesim".

3) Right click on "spacesim" and create a new source folder called "src/main/rules".

4) Move all .drl files into that folder.

5) Download the drools binaries from http://download.jboss.org/drools/release/5.1.1.34858.FINAL/drools-5.1.1-bin.zip.

6) Unpack the binaries.  Through the project settings, add all jar files in the download to the java build path.

7) Compile and run the program.  The main class is SpaceSim.