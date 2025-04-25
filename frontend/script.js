const x2x = "http://172.30.0.27:8080/api/products";

let productIdToDelete = null;

// === API ===
class ProductAPI {
    static async getProducts() {
        try {
            const response = await fetch(x2x);
            if (!response.ok) throw new Error("Error al obtener productos");
            return await response.json();
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async searchProductsByName(name) {
        try {
            const response = await fetch(`${x2x}/filter/name?name=${encodeURIComponent(name)}`);
            if (!response.ok) throw new Error("Error en búsqueda");
            return await response.json();
        } catch (error) {
            console.error(error);
            return [];
        }
    }

    static async addProduct(product) {
        try {
            const response = await fetch(x2x, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(product)
            });
            return await response.json();
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
            return await response.json();
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async deleteProduct(id) {
        try {
            return await fetch(`${x2x}/${id}`, { method: 'DELETE' });
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    static async getById(id) {
        try {
            const response = await fetch(`${x2x}/${id}`);
            if (!response.ok) throw new Error("Error al obtener producto");
            return await response.json();
        } catch (error) {
            console.error(error);
            return null;
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
}

// === Eventos ===
document.addEventListener('DOMContentLoaded', () => {
    UI.displayProducts();

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

        const product = {
            name,
            description,
            price,
            stock,
            createdAt,
            category: { id: categoryId }
        };

        try {
            if (id) {
                await ProductAPI.updateProduct(id, product);
                showToast("✅ Producto actualizado con éxito");
            } else {
                await ProductAPI.addProduct(product);
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

function onCaptchaLoadCallback() {
    formCaptchaWidgetId = grecaptcha.render('formCaptcha', {
        'sitekey': '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    deleteCaptchaWidgetId = grecaptcha.render('deleteCaptcha', {
        'sitekey': '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });
}

// === Protección básica ===
document.addEventListener('contextmenu', e => e.preventDefault());
document.addEventListener('keydown', function(e) {
    if (e.key === "F12" || (e.ctrlKey && e.shiftKey && ["I", "C", "J"].includes(e.key))) {
        e.preventDefault();
    }
});
