// === MANEJO DE PESTAÑAS ===
document.addEventListener('DOMContentLoaded', function () {
    const tabBtns = document.querySelectorAll('.tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            tabBtns.forEach(btn => btn.classList.remove('active'));
            const tabId = this.getAttribute('data-tab');
            const tabContents = document.querySelectorAll('.tab-content');
            tabContents.forEach(tab => tab.classList.remove('active'));

            this.classList.add('active');
            const activeTabContent = document.getElementById(`${tabId}-tab`);
            if (activeTabContent) {
                activeTabContent.classList.add('active');
            }
        });
    });

    const firstTab = tabBtns[0];
    if (firstTab) {
        firstTab.classList.add('active');
        const firstTabId = firstTab.getAttribute('data-tab');
        document.getElementById(`${firstTabId}-tab`).classList.add('active');
    }
});

// === Variables ===
const x2x = "http://172.30.3.160:8080/api/products";
let productIdToDelete = null;

// === API ===
class ProductAPI {
    static async getProducts() {
        try {
            const response = await fetch(x2x);
            if (!response.ok) throw new Error("Error al obtener productos");
            const text = await response.text();
            if (!text) return [];
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async searchProductsByName(name) {
        try {
            const response = await fetch(`${x2x}/filter/name?name=${encodeURIComponent(name)}`);
            if (!response.ok) throw new Error("Error en búsqueda");
            const text = await response.text();
            if (!text) return [];
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async addProduct(payload) {
        try {
            const response = await fetch(x2x, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            const text = await response.text();
            if (!text) return null;
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async updateProduct(id, product) {
        try {
            const response = await fetch(`${x2x}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(product)
            });
            const text = await response.text();
            if (!text) return null;
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async deleteProduct(id) {
        try {
            return await fetch(`${x2x}/${id}?captchaToken=${encodeURIComponent(captchaToken)}`, { method: 'DELETE' });
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async getById(id) {
        try {
            const response = await fetch(`${x2x}/${id}`);
            if (!response.ok) throw new Error("Error al obtener producto");
            const text = await response.text();
            if (!text) return null;
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return null;
        }
    }
}

class CategoryAPI {
    static async getCategories() {
        try {
            const response = await fetch('http://172.30.3.160:8080/api/categories');
            if (!response.ok) throw new Error('Error al obtener categorías');
            const text = await response.text();
            if (!text) return [];
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async addCategory(category, captchaToken) {
        try {
            const payload = {
                captchaToken: captchaToken, // Se agrega el token de captcha
                category: category // El objeto categoría
            };

            const response = await fetch('http://172.30.3.160:8080/api/categories', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            const text = await response.text();
            if (!text) return null;
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
}


// === UI ===
class UI {
    static async displayProducts() {
        const list = document.getElementById('productList');
        list.innerHTML = `<li class="loading">Cargando productos...</li>`;

        const products = await ProductAPI.getProducts();
        list.innerHTML = '';

        if (products.length === 0) {
            list.innerHTML = `
                <li class="empty-state">
                    <i>📦</i>
                    <p>No hay productos agregados aún.</p>
                </li>
            `;
            return;
        }

        const sortedProducts = products.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
        sortedProducts.forEach(product => UI.addProductToList(product));
    }

    static addProductToList(product) {
        const list = document.getElementById('productList');
        const item = document.createElement('li');
        item.classList.add('product-item');
        item.id = `product-${product.id}`;

        item.innerHTML = `
            <div><strong>${UI.escape(product.name)}</strong> - ${UI.escape(product.description || '')}</div>
            <div>💲<strong>${product.price}</strong> - 🧲 Stock: ${product.stock}</div>
            <div class="product-actions">
                <button class="btn btn-warning edit-btn">Editar</button>
                <button class="btn btn-danger delete-btn">Eliminar</button>
            </div>
        `;

        item.querySelector('.edit-btn').addEventListener('click', () => UI.editProduct(product.id));
        item.querySelector('.delete-btn').addEventListener('click', () => UI.confirmDeleteProduct(product.id));

        list.appendChild(item);
    }

    static escape(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    static clearForm() {
        document.getElementById('productForm').reset();
        document.getElementById('productId').value = '';
        grecaptcha.reset(formCaptchaWidgetId);
    }

    static async editProduct(id) {
        const product = await ProductAPI.getById(id);
        if (!product) {
            showToast("❌ No se pudo cargar el producto");
            return;
        }

        document.getElementById('productId').value = product.id;
        document.getElementById('name').value = product.name;
        document.getElementById('description').value = product.description;
        document.getElementById('price').value = product.price;
        document.getElementById('stock').value = product.stock;
        document.getElementById('category').value = product.category.id;
    }

    static confirmDeleteProduct(id) {
        productIdToDelete = id;
        const modal = document.getElementById('deleteModal');
        const product = document.querySelector(`#product-${id} strong`);
        modal.querySelector('.modal-body').textContent = `¿Eliminar el producto "${product.textContent}"?`;
        modal.style.display = 'flex';
    }

    static async search() {
        const query = document.getElementById('searchInput').value.trim();
        if (!query) return;

        const results = await ProductAPI.searchProductsByName(query);
        const list = document.getElementById('productList');
        list.innerHTML = '';

        if (results.length === 0) {
            list.innerHTML = `
                <li class="empty-state">
                    <i>🔍</i>
                    <p>No se encontraron productos</p>
                </li>
            `;
        } else {
            results.forEach(product => UI.addProductToList(product));
        }
    }

    static async loadCategories() {
        const select = document.getElementById('category');
        select.innerHTML = '<option value="">Cargando categorías...</option>';

        const categories = await CategoryAPI.getCategories();
       
        select.innerHTML = '<option value="">Selecciona una categoría</option>';

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.id;
            option.textContent = category.name;
            select.appendChild(option);
        });

        const categoryList = document.getElementById('categoryList');
        if (categoryList) {
            categoryList.innerHTML = '';
            categories.forEach(category => {
                const li = document.createElement('li');
                li.textContent = category.name;
                categoryList.appendChild(li);
            });
        }
    }

    static async addCategory(category) {
        try {
            await CategoryAPI.addCategory(category);
            showToast("✅ Categoría agregada con éxito");
            UI.loadCategories();
        } catch (error) {
            showToast("❌ Error al agregar categoría");
            console.error(error);
        }
    }
}

// === Eventos ===
document.addEventListener('DOMContentLoaded', () => {
    UI.displayProducts();
    UI.loadCategories();

    document.getElementById('categoryForm').addEventListener('submit', async (e) => {
        e.preventDefault();
    
        const captchaToken = grecaptcha.getResponse(categoryFormCaptchaWidgetId);
        if (!captchaToken) {
            showToast("⚠️ Verifica que no eres un robot");
            return;
        }
    
        const categoryName = document.getElementById('categoryName').value.trim();
        if (!categoryName) {
            showToast("⚠️ Ingresa un nombre para la categoría");
            return;
        }
    
        const payload = {
            captchaToken,
            category: { name: categoryName }
        };
    
        try {
            await CategoryAPI.addCategory(payload);
            showToast("✅ Categoría agregada con éxito");
            UI.loadCategories();
            document.getElementById('categoryForm').reset();
            grecaptcha.reset(categoryFormCaptchaWidgetId);
        } catch (error) {
            console.error(error);
            showToast("❌ Error al agregar categoría");
        }
    });
    

    document.getElementById('productForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        const captchaToken = grecaptcha.getResponse(formCaptchaWidgetId);
        if (!captchaToken) {
            showToast("⚠️ Verifica que no eres un robot");
            return;
        }

        const id = document.getElementById('productId').value;
        const name = document.getElementById('name').value.trim();
        const description = document.getElementById('description').value.trim();
        const price = parseFloat(document.getElementById('price').value);
        const stock = parseInt(document.getElementById('stock').value);
        const categoryValue = document.getElementById('category').value;

        if (!name || isNaN(price) || price < 0 || isNaN(stock) || stock < 0 || !categoryValue) {
            showToast("⚠️ Revisa los campos del formulario");
            return;
        }

        const categoryId = parseInt(categoryValue);
        const createdAt = new Date().toISOString();

        const productToSend = {
            captchaToken: captchaToken,
            product: {
                name,
                description,
                price,
                stock,
                createdAt,
                category: { id: categoryId }
            }
        };

        try {
            if (id) {
                await ProductAPI.updateProduct(id, productToSend.product);
                showToast("✅ Producto actualizado con éxito");
            } else {
                await ProductAPI.addProduct(productToSend);
                showToast("✅ Producto agregado con éxito");
            }

            UI.clearForm();
            UI.displayProducts();
        } catch (error) {
            console.error('Error al guardar producto:', error);
            alert('❌ Ocurrió un error al guardar el producto.');
        }
    });

    document.getElementById('searchBtn').addEventListener('click', () => {
        UI.search();
    });

    document.getElementById('confirmDelete').addEventListener('click', async () => {
        const captchaToken = grecaptcha.getResponse(deleteCaptchaWidgetId);
        if (!captchaToken) {
            showToast("⚠️ Verifica que no eres un robot");
            return;
        }

        try {
            const response = await ProductAPI.deleteProduct(productIdToDelete);
            if (response.ok) {
                document.getElementById('deleteModal').style.display = 'none';
                UI.displayProducts();
                showToast("✅ Producto eliminado con éxito");
            } else {
                throw new Error("No se pudo eliminar el producto");
            }
        } catch (error) {
            showToast("❌ Error al eliminar producto");
            console.error(error);
        } finally {
            grecaptcha.reset(deleteCaptchaWidgetId);
        }
    });

    document.getElementById('cancelDelete').addEventListener('click', () => {
        document.getElementById('deleteModal').style.display = 'none';
        grecaptcha.reset(deleteCaptchaWidgetId);
    });
});

// === Toast ===
function showToast(message) {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.classList.remove('hidden');
    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
        toast.classList.add('hidden');
    }, 3000);
}

// === Captcha ===
let formCaptchaWidgetId = null;
let deleteCaptchaWidgetId = null;
let customerFormCaptchaWidgetId = null;
let deleteCustomerCaptchaWidgetId = null;
let categoryFormCaptchaWidgetId = null;

function onCaptchaLoadCallback() {
    formCaptchaWidgetId = grecaptcha.render('formCaptcha', {
        sitekey: '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    deleteCaptchaWidgetId = grecaptcha.render('deleteCaptcha', {
        sitekey: '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    customerFormCaptchaWidgetId = grecaptcha.render('customerFormCaptcha', {
        sitekey: '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    deleteCustomerCaptchaWidgetId = grecaptcha.render('deleteCustomerCaptcha', {
        sitekey: '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    categoryFormCaptchaWidgetId = grecaptcha.render('categoryFormCaptcha', {
        sitekey: '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });
}



// === API para Clientes ===
const customerApiUrl = "http://172.30.3.160:8080/api/customers";

class CustomerAPI {
    static async getCustomers() {
        try {
            const response = await fetch(customerApiUrl);
            if (!response.ok) throw new Error("Error al obtener clientes");
            const text = await response.text();
            if (!text) return [];
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async addCustomer(payload) {
        try {
            const response = await fetch(customerApiUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            if (!response.ok) throw new Error("Error al agregar cliente");
            const text = await response.text();
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async updateCustomer(id, customer) {
        try {
            const response = await fetch(`${customerApiUrl}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(customer)
            });
            if (!response.ok) throw new Error("Error al actualizar cliente");
            const text = await response.text();
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async deleteCustomer(id) {
        try {
            const response = await fetch(`${customerApiUrl}/${id}`, { method: 'DELETE' });
            if (!response.ok) throw new Error("Error al eliminar cliente");
            return response;
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async getById(id) {
        try {
            const response = await fetch(`${customerApiUrl}/${id}`);
            if (!response.ok) throw new Error("Error al obtener cliente");
            const text = await response.text();
            return JSON.parse(text);
        } catch (error) {
            console.error(error);
            return null;
        }
    }
}

// === UI para Clientes ===
class CustomerUI {
    static async displayCustomers() {
        const list = document.getElementById('customerList');
        list.innerHTML = `<li class="loading">Cargando clientes...</li>`;

        const customers = await CustomerAPI.getCustomers();
        list.innerHTML = '';

        if (customers.length === 0) {
            list.innerHTML = `
                <li class="empty-state">
                    <i>👥</i>
                    <p>No hay clientes agregados aún.</p>
                </li>
            `;
            return;
        }

        customers.forEach(customer => CustomerUI.addCustomerToList(customer));
    }

    static addCustomerToList(customer) {
        const list = document.getElementById('customerList');
        const item = document.createElement('li');
        item.classList.add('customer-item');
        item.id = `customer-${customer.id}`;

        item.innerHTML = `
            <div><strong>${CustomerUI.escape(customer.name)}</strong> - ${CustomerUI.escape(customer.email || '')}</div>
            <div class="customer-actions">
                <button class="btn btn-warning edit-btn">Editar</button>
                <button class="btn btn-danger delete-btn">Eliminar</button>
            </div>
        `;

        item.querySelector('.edit-btn').addEventListener('click', () => CustomerUI.editCustomer(customer.id));
        item.querySelector('.delete-btn').addEventListener('click', () => CustomerUI.confirmDeleteCustomer(customer.id));

        list.appendChild(item);
    }

    static escape(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    static clearForm() {
        document.getElementById('customerForm').reset();
        document.getElementById('customerId').value = '';
    }

    static async editCustomer(id) {
        const customer = await CustomerAPI.getById(id);
        if (!customer) {
            showToast("❌ No se pudo cargar el cliente");
            return;
        }

        document.getElementById('customerId').value = customer.id;
        document.getElementById('customerName').value = customer.name;
        document.getElementById('customerEmail').value = customer.email;
    }

    static confirmDeleteCustomer(id) {
        const modal = document.getElementById('deleteCustomerModal');
        const customer = document.querySelector(`#customer-${id} strong`);
        modal.querySelector('.modal-body').textContent = `¿Eliminar al cliente "${customer.textContent}"?`;
        modal.style.display = 'flex';

        // Asignar el ID del cliente al campo de confirmación de eliminación
        document.getElementById('customerIdToDelete').value = id;
    }
}

// === Eventos de Clientes ===
document.addEventListener('DOMContentLoaded', () => {
    CustomerUI.displayCustomers();

    
   // Agregar o editar cliente
document.getElementById('customerForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const captchaToken = grecaptcha.getResponse(customerFormCaptchaWidgetId);
    if (!captchaToken) {
        showToast("⚠️ Verifica que no eres un robot");
        return;
    }

    const name = document.getElementById('customerName').value.trim();
    const email = document.getElementById('customerEmail').value.trim();

    if (!name || !email) {
        showToast("⚠️ Todos los campos son obligatorios");
        return;
    }

    const payload = {
        captchaToken,
        customer: { name, email }
    };

    const customerId = document.getElementById('customerId').value;

    try {
        if (customerId) {
            await CustomerAPI.updateCustomer(customerId, payload.customer);
            showToast("✅ Cliente actualizado con éxito");
        } else {
            await CustomerAPI.addCustomer(payload);
            showToast("✅ Cliente agregado con éxito");
        }
        CustomerUI.displayCustomers();
        document.getElementById('customerForm').reset();
        grecaptcha.reset(customerFormCaptchaWidgetId);
    } catch (error) {
        console.error(error);
        showToast("❌ Error al agregar o actualizar cliente");
    }
});


    // Eliminar Cliente
    document.getElementById('confirmDeleteCustomer').addEventListener('click', async () => {
        const captchaToken = grecaptcha.getResponse(deleteCustomerCaptchaWidgetId);
        if (!captchaToken) {
            showToast("⚠️ Verifica que no eres un robot");
            return;
        }
    
        const customerId = document.getElementById('customerIdToDelete').value;
    
        try {
            await CustomerAPI.deleteCustomer(`${customerId}?captchaToken=${encodeURIComponent(captchaToken)}`);
            document.getElementById('deleteCustomerModal').style.display = 'none';
            CustomerUI.displayCustomers();
            showToast("✅ Cliente eliminado con éxito");
            grecaptcha.reset(deleteCustomerCaptchaWidgetId);
        } catch (error) {
            showToast("❌ Error al eliminar cliente");
            console.error(error);
        }
    });
    
});