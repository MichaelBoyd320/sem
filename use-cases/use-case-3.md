# USE CASE: 3 Produce a Report on the Salary of Employees of a Given Role

## CHARACTERISTIC INFORMATION

### Goal in Context

As an department manager I want to produce a report on the salary of employees in my department so that I can support financial reporting for my department.
### Scope

Company.

### Level

Primary task.

### Preconditions

We know the department.  Database contains current employee salary data.

### Success End Condition

A report is available for DM to provide to finance.

### Failed End Condition

No report is produced.

### Primary Actor

Department Manager.

### Trigger

A request for finance information is sent to DM.

## MAIN SUCCESS SCENARIO

1. Finance request salary information for a given department.
2. DM captures name of their department.
3. DM extracts current salary information of all employees of their department.
4. DM provides report to finance.

## EXTENSIONS

1. **No employees in department**:
    1. DM informs finance no employees exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
