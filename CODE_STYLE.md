# Code Style

This is a code style based off of [official Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).

## Formatting

You do not have to worry about formatting spaces or indentation if you use automatic formatting. The IDE will format your code based on the rules defined in [.editorconfig](.editorconfig).

Use 4 spaces for indentation, not tabs.

Avoid exceeding 120 characters in a single line.

## Nesting

Due to the nature of UI code, there is no limit to how many levels of nesting are allowed.
Just avoid nesting when possible.

For example, use guard clauses instead of repeated if/else statements:

```kotlin
// BAD:
fun foo1(user: User) {
    if (user.age != null) {
        if (user.age < 18) {
            return "Child"
        } else {
            return "Adult"
        }
    } else {
        return "Unknown age"
    }
}

// GOOD:
fun foo2(user: User) {
    if (user.age == null) return "Unknown age"
    if (user.age < 18) return "Child"
    return "Adult"
}
```

## Immutability

If a variable doesn't need to be changed, use `val` instead of `var`.

## Repetition

If you find yourself repeating the same code over and over again, consider separating the common functionality into a function or class.

## Naming rules

Package and class naming rules in Kotlin are quite simple:

* Names of packages are always lowercase and do not use underscores (`org.example.project`). Using multi-word
names is generally discouraged, but if you do need to use multiple words, you can either just concatenate them together
or use camel case (`org.example.myProject`).

* Names of classes and objects use upper camel case:

```kotlin
open class DeclarationProcessor { /*...*/ }

object EmptyDeclarationProcessor : DeclarationProcessor() { /*...*/ }
```

### Function names
 
Names of functions, properties and local variables start with a lowercase letter and use camel case with no underscores:

```kotlin
fun processDeclarations() { /*...*/ }
var declarationCount = 1
```

Exception: factory functions used to create instances of classes can have the same name as the abstract return type:

```kotlin
interface Foo { /*...*/ }

class FooImpl : Foo { /*...*/ }

fun Foo(): Foo { return FooImpl() }
```

### Property names

Names of constants (properties marked with `const`, or top-level or object `val` properties with no custom `get` function
that hold deeply immutable data) should use all uppercase, underscore-separated names following the ([screaming snake case](https://en.wikipedia.org/wiki/Snake_case))
convention:

```kotlin
const val MAX_COUNT = 8
val USER_NAME_FIELD = "UserName"
```

Names of top-level or object properties which hold objects with behavior or mutable data should use camel case names:

```kotlin
val mutableCollection: MutableSet<String> = HashSet()
```

Names of properties holding references to singleton objects can use the same naming style as `object` declarations:

```kotlin
val PersonComparator: Comparator<Person> = /*...*/
```

For enum constants, it's OK to use either all uppercase, underscore-separated ([screaming snake case](https://en.wikipedia.org/wiki/Snake_case)) names
(`enum class Color { RED, GREEN }`) or upper camel case names, depending on the usage. 
   
### Names for backing properties

If a class has two properties which are conceptually the same but one is part of a public API and another is an implementation
detail, use an underscore as the prefix for the name of the private property:

```kotlin
class C {
    private val _elementList = mutableListOf<Element>()

    val elementList: List<Element>
         get() = _elementList
}
```

### Choose good names

The name of a class is usually a noun or a noun phrase explaining what the class _is_: `List`, `PersonReader`.

The name of a method is usually a verb or a verb phrase saying what the method _does_: `close`, `readPersons`.
The name should also suggest if the method is mutating the object or returning a new one. For instance `sort` is
sorting a collection in place, while `sorted` is returning a sorted copy of the collection.

The names should make it clear what the purpose of the entity is, so it's best to avoid using meaningless words
(`Manager`, `Wrapper`) in names.

When using an acronym as part of a declaration name, capitalize it if it consists of two letters (`IOStream`);
capitalize only the first letter if it is longer (`XmlFormatter`, `HttpInputStream`).

## Source code organization

### Source file names

If a Kotlin file contains a single class or interface (potentially with related top-level declarations), its name should be the same
as the name of the class, with the `.kt` extension appended.

If a file contains multiple classes, or only top-level declarations, choose a name describing what the file contains, and name the file accordingly.
Use [upper camel case](https://en.wikipedia.org/wiki/Camel_case), where the first letter of each word is capitalized.
For example, `ProcessDeclarations.kt`.

### Source file organization

Placing multiple declarations (classes, top-level functions or properties) in the same Kotlin source file is encouraged
as long as these declarations are closely related to each other semantically, and the file size remains reasonable
(not exceeding a few hundred lines).

In particular, when defining extension functions for a class which are relevant for all clients of this class,
put them in the same file with the class itself. When defining extension functions that make sense 
only for a specific client, put them next to the code of that client. Avoid creating files just to hold 
all extensions of some class.

### Class layout

The contents of a class should go in the following order:

1. Property declarations and initializer blocks
2. Secondary constructors
3. Method declarations
4. Companion object

Do not sort the method declarations alphabetically or by visibility, and do not separate regular methods
from extension methods. Instead, put related stuff together, so that someone reading the class from top to bottom can 
follow the logic of what's happening. Choose an order (either higher-level stuff first, or vice versa) and stick to it.

Put nested classes next to the code that uses those classes. If the classes are intended to be used externally and aren't
referenced inside the class, put them in the end, after the companion object.

### Interface implementation layout

When implementing an interface, keep the implementing members in the same order as members of the interface (if necessary,
interspersed with additional private methods used for the implementation).

### Overload layout

Always put overloads next to each other in a class.
