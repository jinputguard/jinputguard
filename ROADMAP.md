# Roadmap

## Specialized types
### Object
- Sanitization: `apply(Function)`
- Validation:  `isNotNull`, `isInstanceOf(Class)`, `isEqualTo(T)`
- Mapping: `mapToString()`

### String
- Sanitization: 
  - Operation: `strip()`, `toUpperCase()`, `toLowerCase()`
  - Modification: `prefix(String)`, `suffix(String)`
  - Replacement: `replace(char, char)`, `replace(CharSequence, CharSequence)`, `replaceAll(String, String)`, `replaceAll(Pattern, String)`, `replaceFirst(String, String)`, `replaceFirst(Pattern, String)`
- Validation
  - Length: `isNotEmpty()`, `hasMaxLength(int)`, `hasMinLength(int)`, `hasLength(int)`, `hasLengthBetween(int, int)`
  - Match: `matches(String)`, `matches(Pattern)`
- Mapping
  - To Number: `parseToInteger()`, `parseToLong()`, `parseToFloat()`, `parseToDouble()`
  - To UUID: `parseToUuid()`

### Number: Integer, Long, Float, Double
- Sanitization: 
  - Clamp: `clamp(T, T)`, `clampMin(T)`, `clampMax(T)`
- Validation
  - Comparison: `isGreaterThan(T)`, `isGreaterOrEqualTo(T)`, `isLowerThan(T)`, `isLowerOrEqualTo(T)`, `isBetween(T, T)`
  - Relation to zero: `isPositive()`, `isPositiveOrNul()`, `isZero()`, `isNegative()`, `isNegativeOrNul()`
- Mapping: 
  - `mapToInt()`, `mapToLong()`, `mapToDouble()`, `mapToFloat()`

### UUID
- Validation
  - Version: `isV1()`, `isV2()`, `isV3()`, `isV4()`, `isV5()`, `isV6()`, `isV7()`, `isV8()`
  - Nil: `isNotNilUUID()`
  
### Sequenced collections: List
- Processing: `processEach(InputGuard<T, OUT>)`, `processEach(InputGuard<T, OUT>, Collector<OUT, ?, List<OUT>>)`

### Collecions: Set
- Processing: `processEach(InputGuard<T, OUT>)`, `processEach(InputGuard<T, OUT>, Collector<OUT, ?, List<OUT>>)`


## To do
- Revamp/cleanup `null` handling
- Move to JSpecify annotations for nullability
- More specialized methods:
  - String
    - Validation
    - Mapping: 
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
  - [ ] Path

## Backlog / To analyze / To implement if requested
- [ ] ???
