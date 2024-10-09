# USE CASE: 7 Delete Employee Details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an HR advisor I want to delete an employee's details so that the company is compliant with data retention legislation.

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the Employee and their details.  Database contains current employee salary data.

### Success End Condition

The employees details are no longer in the system.

### Failed End Condition

No details are deleted.

### Primary Actor

HR Advisor.

### Trigger

An employee is no longer with the company.

## MAIN SUCCESS SCENARIO

1. Employee leaves the company.
2. HR advisor captures name of the employee.
3. HR advisor deletes the details of the employee.

## EXTENSIONS

1. **Employee already does not exist**:
    1. Does not proceed.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
