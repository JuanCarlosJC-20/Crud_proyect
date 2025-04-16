const API_URL = 'http://localhost:8080/api/products';

class ProductAPI {
    static async getProducts() {
        const response = await fetch(API_URL);
        return await response.json();
    }

    static async searchProductsByName(name) {
        const response = await fetch(`${API_URL}/filter/name?name=${encodeURIComponent(name)}`);
        return await response.json();
    }

    static async addProduct(product) {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return await response.json();
    }

    static async updateProduct(id, product) {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return await response.json();
    }

    static async deleteProduct(id) {
        return await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
    }

    static async getById(id) {
        const response = await fetch(`${API_URL}/${id}`);
        return await response.json();
    }
}

class UI {
    static async displayProducts() {
        const products = await ProductAPI.getProducts();
        const list = document.getElementById('productList');
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
    
        // Ordenar por fecha de creación descendente
        const sortedProducts = products.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    
        sortedProducts.forEach(product => UI.addProductToList(product));
    }

    static addProductToList(product) {
        const list = document.getElementById('productList');
        const item = document.createElement('li');
        item.classList.add('product-item');

        item.innerHTML = `
            <div><strong>${product.name}</strong> - ${product.description || ''}</div>
            <div>💲<strong>${product.price}</strong> - 🧮 Stock: ${product.stock}</div>
            <div class="product-actions">
                <button class="btn btn-warning" onclick="UI.editProduct(${product.id})">Editar</button>
                <button class="btn btn-danger" onclick="UI.deleteProduct(${product.id})">Eliminar</button>
            </div>
        `;

        list.appendChild(item); // ✅ Ya no usamos insertBefore
    }

    static clearForm() {
        document.getElementById('productForm').reset();
        document.getElementById('productId').value = '';
    }

    static async editProduct(id) {
        const product = await ProductAPI.getById(id);
        document.getElementById('productId').value = product.id;
        document.getElementById('name').value = product.name;
        document.getElementById('description').value = product.description;
        document.getElementById('price').value = product.price;
        document.getElementById('stock').value = product.stock;
        document.getElementById('category').value = product.category.id;
    }

    static async deleteProduct(id) {
        await ProductAPI.deleteProduct(id);
        UI.displayProducts();
    }

    static async search() {
        const query = document.getElementById('searchInput').value;
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

document.addEventListener('DOMContentLoaded', () => {
    UI.displayProducts();
});

document.getElementById('productForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('productId').value;
    const name = document.getElementById('name').value;
    const description = document.getElementById('description').value;
    const price = parseFloat(document.getElementById('price').value);
    const stock = parseInt(document.getElementById('stock').value);
    const categoryValue = document.getElementById('category').value;

    if (!categoryValue) {
        alert('Selecciona una categoría válida');
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

    console.log("📤 JSON que se enviará:", JSON.stringify(product, null, 2));

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
        alert('Ocurrió un error al guardar el producto. Revisa la consola.');
    }
});

document.getElementById('searchBtn').addEventListener('click', () => {
    UI.search();
});

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
