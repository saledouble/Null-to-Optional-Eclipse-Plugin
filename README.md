# constants-to-enum-eclipse-plugin
A research prototype Eclipse plugin that refactors Java legacy code to make use of the new enum program construct that was introduced in Java 5.

The Convert Constants to Enum refactoring for Eclipse provides an automated approach for transforming legacy Java code to use the new enumeration construct. This semantics-preserving tool increases type safety, produces code that is easier to comprehend, removes unnecessary complexity, and eliminates brittleness problems that normally prevent separate compilation.

![http://constants-to-enum-eclipse-plugin.googlecode.com/files/Screen%20shot%202010-09-17%20at%203.16.37%20PM.png](http://constants-to-enum-eclipse-plugin.googlecode.com/files/Screen%20shot%202010-09-17%20at%203.16.37%20PM.png)

The plugin refactors Java legacy code to make use of the new enum program construct which was introduced in Java 1.5. This construct, which provides language support for enumerated types, is one of many new features of Java that offer significant improvements over older Java technology. Prior to Java 1.5, programmers were required to employ various design patterns (e.g., static final int ...) to compensate for the absence of enumerated types in Java. Unfortunately, these compensation patterns lack several highly-desirable properties of the enum construct, most notably, type safety.

## Update Site #

http://constants-to-enum-eclipse-plugin.googlecode.com/svn/edu.ohio_state.cse.khatchad.refactoring.updatesite

## News #
There are several features currently lacking in the plugin which are obstacles to wide distribution including a robust graphical user interface, a full test suite with regression tests, and complete undo functionality.

## Getting started #

Take a look at the [Documentation](Documentation.md) page. In short, install the plugin via the update site, select a set of constants explicitly through the package explorer or outline view, and select the "Convert Constants to Enum" option from the context menu. A wizard will then display with further instructions and customizations. In future versions, we hope to have the command available at multiple levels (e.g., projects) and from the editor and drop down menu.

### Alternate instructions

Go to the Outline View (Window -> Show View -> Outline), then in the Outline View select (using Ctrl + Click) all the constants you want to convert. Right click on them, and select Refactor -> Convert Constants to Enum... from the right-click menu.

## Getting help #

Please feel free to post to the [General Development Discussion](http://groups.google.com/group/convert-constants-to-enum-dev) list with any comments, questions, or concerns.