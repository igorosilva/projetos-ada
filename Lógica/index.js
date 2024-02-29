import { Task } from './classes/Task.js';

let tasks = [];
let taskName, taskDescription, isDoneInput;
let isDone;
let choice;

do {
    choice = parseInt(prompt("Escolha uma opção:\n1 - Adicionar Tarefa\n2 - Editar Tarefa\n3 - Remover Tarefa\n4 - Listar todas tarefas\n5 - Obter tarefa\n6 - Sair"));

    switch (choice) {
        case 1:
            addTask();
            break;

        case 2:
            let editOption;
            let editValue;

            var taskId = undefined;

            while (isNaN(taskId) || taskId === undefined) {
                taskId = parseInt(prompt("Digite o ID da tarefa que vai ser editada:"));
            }

            do {
                editOption = parseInt(prompt("Digite a propriedade que deseja alterar:\n1 - Nome\n2 - Descrição\n3 - Se está concluído\n4 - Cancelar"));

                switch (editOption) {
                    case 1:
                        taskName = prompt("Digite o novo nome:");

                        if (inputIsNull(taskName)) {
                            continue;
                        } else {
                            editOption = 1;
                            editValue = taskName

                            editTask(taskId, getAttributeName(editOption), editValue);

                            break;
                        }

                    case 2:
                        taskDescription = prompt("Digite a nova descrição:");

                        if (inputIsNull(taskDescription)) {
                            continue;
                        } else {
                            editOption = 2;
                            editValue = taskDescription;

                            editTask(taskId, getAttributeName(editOption), editValue);

                            break;
                        }


                    case 3:
                        isDoneInput = prompt("Digite se está conclúido ou não no formato: 'True' ou 'False'")

                        if (isDoneInput.toLowerCase() === "true" || isDoneInput.toLowerCase() === "false") {
                            isDone = isDoneInput.toLowerCase();

                            editOption = 3;
                            editValue = isDone;

                            editTask(taskId, getAttributeName(editOption), editValue);

                            break;
                        } else {
                            alert("Formato inválido!")

                            continue;
                        }

                    case 4:
                        break;

                    default:
                        alert("Opção inválida!");
                }


            } while (editOption != 4)

            break;

        case 3:
            var taskId = undefined;

            while (isNaN(taskId) || taskId === undefined) {
                taskId = parseInt(prompt("Digite o ID da tarefa que vai ser editada:"))
            }

            deleteTask(taskId);
            break;

        case 4:
            listAllTasks();
            break;

        case 5:
            var taskId = undefined;

            while (isNaN(taskId) || taskId === undefined) {
                taskId = parseInt(prompt("Digite o ID da tarefa que deseja deletar:"));
            }

            getTask(taskId);
            break;

        case 6:
            break;

        default:
            alert("Opção inválida!");
    }
} while (choice != 6)

function addTask() {
    do {
        taskName = prompt("Digite o nome da tarefa:");
        taskDescription = prompt("Digite uma descrição para a tarefa:");

        if (!inputIsNull(taskName) && !inputIsNull(taskDescription)) {
            let task = new Task(generateTaskID(tasks), taskName, taskDescription, false);

            tasks.push(task);
            break;
        } else {
            alert("Todos os campos devem ser preenchidos!");
        }
    } while (true)
}

function editTask(id, attribbute, value) {
    tasks.forEach(task => {
        if (task.id === id) {
            task[attribbute] = value
        }
    })
}

function deleteTask(id) {
    const index = tasks.findIndex(task => task.id === id);

    if (index !== -1) {
        tasks.splice(index, 1);

        console.log(`Tarefa com ID ${id} removida com sucesso.`);
    } else {
        console.log(`Tarefa com ID ${id} não encontrada.`);
    }
}

function listAllTasks() {
    tasks.forEach(task => {
        console.log(`ID: ${task.id}\nName: ${task.name}\nDescription: ${task.description}\nIs done: ${task.isDone}`);
    });
}

function getTask(id) {
    const filteredTasks = tasks.filter(task => task.id === id);

    if (filteredTasks.length > 0) {
        const task = filteredTasks[0];
        console.log(`ID: ${task.id}\nName: ${task.name}\nDescription: ${task.description}\nIs done: ${task.isDone}`);
    } else {
        console.log("Tarefa não encontrada!");
    }
}

function generateTaskID() {
    if (tasks.length === 0) {
        return 1;
    } else {
        let lastTaskID = tasks[tasks.length - 1].id;

        return lastTaskID + 1;
    }
}

function inputIsNull(input) {
    return input.trim() === '';
}

function getAttributeName(option) {
    switch (option) {
        case 1:
            return "name";

        case 2:
            return "description";

        case 3:
            return "isDone";

        default:
            return "";
    }
}