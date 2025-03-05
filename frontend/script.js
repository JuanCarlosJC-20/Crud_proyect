document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("crud-form");
    const itemList = document.getElementById("item-list");
    let items = [];

    form.addEventListener("submit", (e) => {
        e.preventDefault();
        const nameInput = document.getElementById("name");
        if (nameInput.value.trim() !== "") {
            addItem(nameInput.value);
            nameInput.value = "";
        }
    });

    function addItem(name) {
        const item = { id: Date.now(), name };
        items.push(item);
        renderItems();
    }

    function renderItems() {
        itemList.innerHTML = "";
        items.forEach(item => {
            const li = document.createElement("li");
            li.textContent = item.name;

            const editButton = document.createElement("button");
            editButton.textContent = "Editar";
            editButton.classList.add("edit");
            editButton.onclick = () => editItem(item.id);

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Eliminar";
            deleteButton.classList.add("delete");
            deleteButton.onclick = () => deleteItem(item.id);

            li.appendChild(editButton);
            li.appendChild(deleteButton);
            itemList.appendChild(li);
        });
    }

    function editItem(id) {
        const newName = prompt("Editar nombre del elemento:", items.find(item => item.id === id).name);
        if (newName) {
            items = items.map(item => item.id === id ? { ...item, name: newName } : item);
            renderItems();
        }
    }

    function deleteItem(id) {
        items = items.filter(item => item.id !== id);
        renderItems();
    }
});