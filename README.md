

<h3 align="center">Symbolic Differentiation Calculator</h3>

---

<p align="center"> This is a Graphing UI implemented into Java that parses and evaluates characteristics of equations.
    <br> 
</p>

##  Table of Contents
- [About](#about)
- [Getting Started](#getting_started)
- [Built Using](#built_using)

##  About <a name = "about"></a>
This calculator was a learning project for me to familiarize myself with creating more complex software. It uses an implementation of the [Shunting-Yard Algorithim](https://www.google.com/search?client=firefox-b-1-d&q=shunting+yard+algorithm)
to parse inputted equations into RPN, where it is then used in tandem with a stack-based expression evaluator to evaluate functions in R^2. Using the JavaFX
library, I created a Graphing UI that accepts real-time expression inputs and charts them onto the graph. The UI uses a numeric derivation estimation (a combination of Newton's
method and the Bisection Method) to
identify extremas and points of concavity on the graph. The calculator also has the capability to symbolically differentiate functions. Using a recursive-based algorithm,
the program will provide an unsimplified derivative (currently the program does not simplify outputs so the derivative of [x,2,-] would be [1,0,-] instead of [1] of 
an expression given its RPN).
As it stands, the calculator currently has support for the following functions:
- Trigonometric Functions
- Exponential Functions
- Polynomial Functions (with removable discontinuity support)
- Logarithmic Functions (base x and base e)


##  Getting Started <a name = "getting_started"></a>

### Prerequisites
A JavaFX SDK is required to run this project. In order to add one to the Project, you can go download the package at [Gluon](https://gluonhq.com/products/javafx/). Once you download the package,]
open the project and navigate to File > Project Settings > Libraries and click the plus button. Select the JDK downloaded from Gluon. After that the JDK library should be integrated
into the project. [Here](https://stackoverflow.com/questions/52682195/how-to-get-javafx-and-java-11-working-in-intellij-idea) is another helpful resource to getting the JDK setup.

### Installing
There shouldn't be any additional setup past downloading the files and defining the file structure/compiling. To define the file structure in Intellij, go to 
Open the Project, then go to File > Project Settings > Modules. Then, select the resource folder and give it a resource label, and provide the src folder with a source label.
The program should then compile without errors.

## Built Using <a name = "built_using"></a>
- [JavaFX](https://openjfx.io//) - UI + Charting Framework
- [Scene Builder](https://gluonhq.com/products/scene-builder/) - UI Design Tool

