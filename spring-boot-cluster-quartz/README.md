# 工程简介
In this tutorial we'll build a simple Scheduler in Spring with Quartz.

We'll begin with a simple goal in mind – to easily configure a new scheduled job.

## Quartz API中的一些关键组件

Quartz has a modular architecture. It consists of several basic components that can be combined as required. In this tutorial, we'll focus on the ones that are common to every job: Job, JobDetail, Trigger and Scheduler.

Although we will use Spring to manage the application, each individual component can configured in two ways: the `Quartz way` or the `Spring way` (using its convenience classes).

We will cover both as far as possible for the sake of completeness, but either may be adopted. Let's start building, one component at a time.

# 延伸阅读
- [spring-quartz-schedule](https://www.baeldung.com/spring-quartz-schedule)

