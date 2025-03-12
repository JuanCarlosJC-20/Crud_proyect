document.addEventListener("DOMContentLoaded", () => {
    const registerForm = document.getElementById("registerForm");
    const menuList = document.getElementById("menuList");
    const searchInput = document.getElementById("search");
    let menu = [];
    let editingId = null;
    
    registerForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const name = document.getElementById("dishName").value;
        const price = document.getElementById("price").value;
        const category = document.getElementById("category").value;
        
        if (editingId) {
            // Editar platillo existente
            const dishIndex = menu.findIndex(dish => dish.id === editingId);
            menu[dishIndex] = { id: editingId, name, price, category };
            editingId = null;
        } else {
            // Agregar nuevo platillo
            const newDish = {
                id: menu.length + 1,
                name,
                price,
                category
            };
            menu.push(newDish);
        }
        renderMenu();
        registerForm.reset();
    });

    function renderMenu(filter = "") {
        menuList.innerHTML = "";
        menu.filter(dish => 
            dish.name.toLowerCase().includes(filter) ||
            dish.price.includes(filter) ||
            dish.category.toLowerCase().includes(filter)
        ).forEach(dish => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${dish.id}</td>
                <td>${dish.name}</td>
                <td>${dish.price}</td>
                <td>${dish.category}</td>
                <td>
                    <button onclick="editDish(${dish.id})">Editar</button>
                    <button onclick="deleteDish(${dish.id})">Eliminar</button>
                </td>
            `;
            menuList.appendChild(row);
        });
    }

    searchInput.addEventListener("input", (event) => {
        renderMenu(event.target.value.toLowerCase());
    });

    window.deleteDish = (id) => {
        menu = menu.filter(dish => dish.id !== id);
        renderMenu();
    };

    window.editDish = (id) => {
        const dish = menu.find(dish => dish.id === id);
        if (dish) {
            document.getElementById("dishName").value = dish.name;
            document.getElementById("price").value = dish.price;
            document.getElementById("category").value = dish.category;
            editingId = id;
        }
    };
});