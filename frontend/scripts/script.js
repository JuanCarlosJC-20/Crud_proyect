document.addEventListener("DOMContentLoaded", () => {
    const registerForm = document.getElementById("registerForm");
    const inventoryList = document.getElementById("inventoryList");
    const searchInput = document.getElementById("search");
    let inventory = [];
    let editingId = null;
    
    registerForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const name = document.getElementById("productName").value;
        const price = document.getElementById("price").value;
        const category = document.getElementById("category").value;
        
        if (editingId) {
            // Editar producto existente
            const productIndex = inventory.findIndex(product => product.id === editingId);
            inventory[productIndex] = { id: editingId, name, price, category };
            editingId = null;
        } else {
            // Agregar nuevo producto
            const newProduct = {
                id: inventory.length + 1,
                name,
                price,
                category
            };
            inventory.push(newProduct);
        }
        renderInventory();
        registerForm.reset();
    });

    function renderInventory(filter = "") {
        inventoryList.innerHTML = "";
        inventory.filter(product => 
            product.name.toLowerCase().includes(filter) ||
            product.price.includes(filter) ||
            product.category.toLowerCase().includes(filter)
        ).forEach(product => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td><strong>${product.id}<strong></td>
                <td><strong>${product.name}</strong></td>
                <td><strong>${product.price}</strong></td>
                <td><strong>${product.category}</strong></td>
                <td>
                    <button onclick="editProduct(${product.id})">Editar</button>
                    <button onclick="deleteProduct(${product.id})">Eliminar</button>
                </td>
            `;
            inventoryList.appendChild(row);
        });
    }

    searchInput.addEventListener("input", (event) => {
        renderInventory(event.target.value.toLowerCase());
    });

    window.deleteProduct = (id) => {
        inventory = inventory.filter(product => product.id !== id);
        renderInventory();
    };

    window.editProduct = (id) => {
        const product = inventory.find(product => product.id === id);
        if (product) {
            document.getElementById("productName").value = product.name;
            document.getElementById("price").value = product.price;
            document.getElementById("category").value = product.category;
            editingId = id;
        }
    };
});
