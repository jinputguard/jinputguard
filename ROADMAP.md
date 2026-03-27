# Roadmap

## Specialized types
### Object
- Sanitization: `apply(Function)`
- Validation:  `isNotNull`, `isInstanceOf(Class)`, `isEqualTo(T)`

### String
- Sanitization: 
  - Operation: `strip()`, `toUpperCase()`, `toLowerCase()`
  - Modification: `prefix(String)`, `suffix(String)`
  - Replacement: `replace(char, char)`, `replace(CharSequence, CharSequence)`, `replaceAll(String, String)`, `replaceAll(Pattern, String)`, `replaceFirst(String, String)`, `replaceFirst(Pattern, String)`
- Validation
  - Length: `isNotEmpty()`, `isMaxLength(int)`
  - Match: `matches(String)`, `matches(Pattern)`
- Mapping
  - To Number: `mapToInteger()`, `mapToLong()`

### Number: Integer, Long, Float, Double
- Sanitization: 
  - Clamp: `clamp(T, T)`, `clampMin(T)`, `clampMax(T)`
- Validation
  - Comparison: `isGreaterThan(T)`, `isGreaterOrEqualTo(T)`, `isLowerThan(T)`, `isLowerOrEqualTo(T)`, `isBetween(T, T)`
  - Relation to zero: `isPositive()`, `isPositiveOrNul()`, `isZero()`, `isNegative()`, `isNegativeOrNul()`

### Sequenced collections: List
- Processing: `processEach(InputGuard<T, OUT>)`, `processEach(InputGuard<T, OUT>, Collector<OUT, ?, List<OUT>>)`

### Collecions: Set
- Processing: `processEach(InputGuard<T, OUT>)`, `processEach(InputGuard<T, OUT>, Collector<OUT, ?, List<OUT>>)`


## To do
- Revamp/cleanup `null` handling
- Move to JSpecify annotations for nullability
- More specialized methods:
  - Object
    - Mapping: `mapToString()`
  - String
    - Validation
      - Length: `isMinLength(int)`, `isLength(int)`, `isLengthBetween(int, int)`
    - Mapping: 
      - To Number: `mapToDouble()`, `mapToFloat()`
      - To UUID: `mapToUuid()`
  - Number - Integer
    - Mapping: 
      - Integer to other Number: `mapToLong()`, `mapToDouble()`, `mapToFloat()`
      - Long to other Number: `mapToInteger()`, `mapToDouble()`, `mapToFloat()`
      - Double to other Number: `mapToInteger()`, `mapToLong()`, `mapToFloat()`
      - Float to other Number: `mapToInteger()`, `mapToLong()`, `mapToDouble()`
  - Sequenced collections
    - Validation
      - Length: `isNotEmpty()`, `isMinSize(int)`, `isMaxSize(int)`, `isSize(int)`, `isSizeBetween(int, int)`
      - Duplication: `doesNotContainDuplicates()`, `doesNotContainDuplicates(Function<T, ?>)`
  - Collections
    - Validation
      - Length: `isNotEmpty()`, `isMinSize(int)`, `isMaxSize(int)`, `isSize(int)`, `isSizeBetween(int, int)`
      - Duplication: `doesNotContainDuplicates()`, `doesNotContainDuplicates(Function<T, ?>)`
- More specialized builders
  - [ ] Array
  - [ ] UUID
  - [ ] Path

## Backlog / To analyze / To implement if requested
- [ ] ???
