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

### List

### Set


## To do
- [ ] Revamp/cleanup `null` handling
- [] Move to JSpecify annotations for nullability
- More validation
  - [ ] String - Validation - Length: `isMinLength(int)`, `isLength(int)`, `isLengthBetween(int, int)`
- More mapping
- [ ] Object - Mapping: `mapToString()`
- [ ] String - Mapping: `mapToDouble()`, `mapToFloat()`, `mapToUuid()`
- [ ] Integer - Mapping: `mapToLong()`, `mapToDouble()`, `mapToFloat()`
- [ ] Long - Mapping: `mapToInteger()`, `mapToDouble()`, `mapToFloat()`
- [ ] Double - Mapping: `mapToInteger()`, `mapToLong()`, `mapToFloat()`
- [ ] Float - Mapping: `mapToInteger()`, `mapToLong()`, `mapToDouble()`
- More specialized builders
  - [ ] Array
  - [ ] UUID
  - [ ] Path

## Backlog / To analyze / To implement if requested
- [ ] ???
