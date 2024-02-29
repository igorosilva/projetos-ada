export class Task {
    id;
    name;
    description;
    isDone;

    constructor(id, taskName, taskDescription, taskIsDone) {
        this.id = id;
        this.name = taskName;
        this.description = taskDescription;
        this.isDone = taskIsDone
    }

    generateTaskID(tasks) {
        if (tasks.length === 0) {
            return 1;
        } else {
            let lastTaskID = tasks[tasks.length - 1].id;

            return lastTaskID + 1;
        }
    }
}