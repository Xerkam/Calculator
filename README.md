

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
- Rational Functions (As of this update asymptotes are not currently recorded onto the graph)


##  Getting Started <a name = "getting_started"></a>

### Prerequisites
There are no requirements to run this project. The user just needs to clone the repo and run Main() to get access to the graphing UI. However, I have only tested opening the project through Intellij using their built in version control fetcher. It is possible that installing the software in other ways may require additional work to build the program such as redefining project structures.

### Installing
1. Copy the HTTPS web URL of this GitHub Project
2. Open Intellij Idea and navigate to the welcome menu
3. Click 'Get from Version Control' and complete the steps needed to log in to your GitHub Account
4. Paste the copied web URL into the 'URL' section and select the directory where you want the projec to reside
5. Wait until the project structure fully intializes, then run Main()


## Built Using <a name = "built_using"></a>
- [JavaFX](https://openjfx.io//) - UI + Charting Framework
- [Maven](http://maven.apache.org/what-is-maven.html) - Dependency Manager
- [Scene Builder](https://gluonhq.com/products/scene-builder/) - UI Design Tool

