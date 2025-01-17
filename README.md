# MathElite  

## Overview  
**MathElite** is an advanced yet user-friendly calculator app designed to solve mathematical expressions efficiently and intuitively. Featuring a sleek splash screen and a powerful expression evaluation engine, MathElite uses **infix-to-prefix conversion** to handle and compute any valid arithmetic expression. The app ensures accurate results while detecting and notifying users of mathematical or syntax errors.  

---

## Features  

### 1. **Splash Screen**  
- Engaging splash screen displayed on app launch, leading to the calculator interface.  

### 2. **Calculator Interface**  
- **Expression Input**: Users can type any mathematical expression involving basic arithmetic operations (+, -, *, /).  
- **Expression Evaluation**:  
  - Converts infix expressions to prefix notation for accurate computation.  
  - Supports parentheses and complex expressions.  
- **Error Detection**:  
  - Detects and notifies users of **math errors** (e.g., division by zero).  
  - Handles **syntax errors** (e.g., mismatched parentheses, invalid operators).  
- **Scrollable Output Section**: Displays results for larger expressions without truncation.  

### 3. **Error Handling**  
- **Division by Zero**: Displays a specific error message if a division by zero is attempted.  
- **Syntax Error**: Alerts users about malformed or invalid expressions.  

---

## Technology Stack  
- **Programming Language**: Kotlin  
- **Expression Parsing**: Infix-to-Prefix Conversion Algorithm  
- **UI Framework**: Jetpack Compose (or XML-based layouts)  

---

## How to Use  
1. **Launch the App**: Start MathElite to view the splash screen before entering the calculator interface.  
2. **Input Expression**: Type a valid arithmetic expression (e.g., `(5 + 3) * 2 / 4`).  
3. **View Results**:  
   - The app evaluates the expression and displays the result in the scrollable output section.  
   - For invalid inputs, appropriate error messages will be shown.  
4. **Handle Errors**: Correct the input if prompted with a math or syntax error.  

---

## Key Algorithms  
1. **Infix-to-Prefix Conversion**:  
   - Ensures correct operator precedence and parenthesis handling.  
   - Provides robust parsing for complex mathematical expressions.  
2. **Error Handling Logic**:  
   - Validates expressions before evaluation.  
   - Checks for zero division, invalid characters, and unbalanced parentheses.
   - This is obtained by having stack underflow in the evaluation process

---

## Future Enhancements  
- **Advanced Operations**: Support for trigonometric, logarithmic, and exponential functions.  
- **History Feature**: Save and recall previous calculations.  
- **Dark Mode**: Offer a dark theme for better usability in low-light conditions.  
- **Customization Options**: Allow users to choose font size and UI themes.  

---

## Installation Instructions  
1. Clone the repository:  
   ```bash
   git clone https://github.com/username/MathElite.git
   ```  
2. Open the project in Android Studio.  
3. Build and run the project on your Android device or emulator.  

---
