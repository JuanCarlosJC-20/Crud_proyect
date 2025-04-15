document.addEventListener("DOMContentLoaded", () => {
    // Elementos del DOM
    const registerForm = document.getElementById("registerForm");
    const inventoryList = document.getElementById("inventoryList");
    const searchInput = document.getElementById("search");
    const inventoryModal = document.getElementById("inventoryModal");
    const inventoryBtn = document.getElementById("inventoryBtn");
    const closeBtn = document.querySelector(".close");
    
    let inventory = [];
    let editingId = null;
    
    // Abrir modal cuando se hace clic en el botón de inventario
    inventoryBtn.addEventListener("click", (event) => {
        event.preventDefault();
        inventoryModal.style.display = "block";
        renderInventory();
    });
    
    // Cerrar modal con la X
    closeBtn.addEventListener("click", () => {
        inventoryModal.style.display = "none";
    });
    
    // Cerrar modal haciendo clic fuera del contenido
    window.addEventListener("click", (event) => {
        if (event.target === inventoryModal) {
            inventoryModal.style.display = "none";
        }
    });
    
    // Añadir o actualizar producto
    registerForm.addEventListener("submit", (event) => {
        event.preventDefault();
        alert("Producto Registrado Con Exito , Consulte En INVENTARIO");
        const name = document.getElementById("productName").value;
        const price = document.getElementById("price").value;
        const category = document.getElementById("category").value;
        
        if (editingId) {
            // Editar producto existente
            const productIndex = inventory.findIndex(product => product.id === editingId);
            inventory[productIndex] = { id: editingId, name, price, category };
            editingId = null;
            
            // Volver a mostrar el modal después de editar
            inventoryModal.style.display = "block";
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

    // Renderizar inventario con filtro opcional
    function renderInventory(filter = "") {
        inventoryList.innerHTML = "";
        inventory.filter(product => 
            product.name.toLowerCase().includes(filter) ||
            product.price.includes(filter) ||
            product.category.toLowerCase().includes(filter)
        ).forEach(product => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td><strong>${product.id}</strong></td>
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

    // Filtrar inventario
    searchInput.addEventListener("input", (event) => {
        renderInventory(event.target.value.toLowerCase());
    });

    // Eliminar producto
    window.deleteProduct = (id) => {
        inventory = inventory.filter(product => product.id !== id);
        renderInventory();
    };

    // Editar producto
    window.editProduct = (id) => {
        const product = inventory.find(product => product.id === id);
        if (product) {
            document.getElementById("productName").value = product.name;
            document.getElementById("price").value = product.price;
            document.getElementById("category").value = product.category;
            editingId = id;
            
            // Cerrar el modal al editar
            inventoryModal.style.display = "none";
            
            // Desplazarse al formulario
            document.getElementById("register").scrollIntoView({ behavior: "smooth" });
        }
    };
});