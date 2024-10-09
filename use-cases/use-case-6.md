# USE CASE: 6 View Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an HR advisor I want to view and employee's details so that the employee's promotion request can be supported.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the Employee.  Database contains current employee data.

### Success End Condition

A report is available for the HR Advisor.

### Failed End Condition

No details are shown.

### Primary Actor

HR Advisor.

### Trigger

A request for a promotion is sent to HR.

## MAIN SUCCESS SCENARIO

1. Employee produces a promotion request.
2. HR advisor captures name and identifying data of the employee.
3. HR advisor extracts current employee information of the employee.
4. HR advisor provides now can view employee details.

## EXTENSIONS

1. **Employee does not exist**:
    1. Dont proceed.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
