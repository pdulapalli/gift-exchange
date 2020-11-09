# gift-exchange

## Assumptions

These assumptions are made for simplification purposes:
* All family members will have unique names
* Each individual will have zero or two parents
  * Zero parents should happen at the highest level of the family representation to ensure finite bounds
* Each individual will have zero or one spouse
  * If the individual has children, then he/she has one spouse
* An individual will never have both zero parents and no spouse

## Building

**NOTE: Please clean and rebuild when checking out different commits in the sourcetree.**

### Tests

Gradle will run the test stage (called `test`) automatically during the `build` stage.

### Docker

Assuming that Docker has been installed for your OS.

Please execute the following in the terminal:

```
docker build --no-cache -t gift-exchange .
```

### Native

Please execute the following in the terminal:

```
./gradlew clean
./gradlew build
```

## Running

### Input

Please create a file that has a list of all your family members.
The contents of this file will be passed into `stdin` for the program.

### Format

Use a separate line for each individual in your family.

The structure should resemble the following:
```
<firstName>,<parent1>,<parent2>,<spouse>
```

If the individual has no listed parents (highest-level), that will look like:
```
<firstName>,,,<spouse>
```

If the individual has no spouse, that will look like:
```
<firstName>,<parent1>,<parent2>
```

### Examples

#### Example 1

Available as a text file [here](examples/compatibleOnlyWithLooseFilter.txt)

Relationships
* John is the child of Roger and Mary.
* John is married to Sarah.
* John and Sarah have two children: Carl and Patricia.
* Sarah's parents, Eric and Diana, are not present in the exchange.

Representation
```
John,Roger,Mary,Sarah
Sarah,Eric,Diana,John
Roger,,,Mary
Mary,,,Roger
Carl,John,Sarah
Patricia,John,Sarah
```

### Output Format

A list of family members with their designated gift recipients, comma-separated.

Will resemble this:
```
GIFTER,RECIPIENT
<personA>,<personB>
...
<personX>,<personY>
```

### Docker

Assumes the `gift-exchange` container image was built.

Please execute the following:

```
cat ./familyMembersList.txt | docker run -i gift-exchange
```

### Native

Assumes that the Java 8 Runtime is installed.

Please execute the following:

```
cat ./familyMembersList.txt | java -jar ./build/libs/giftexchange.jar
```