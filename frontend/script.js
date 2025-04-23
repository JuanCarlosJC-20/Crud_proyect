const x2x = "http://192.168.125.68:8080/api/products";

let productIdToDelete = null;

class ProductAPI {
    static async getProducts() {
        const response = await fetch(x2x);
        return await response.json();
    }

    static async searchProductsByName(name) {
        const response = await fetch(`${x2x}/filter/name?name=${encodeURIComponent(name)}`);
        return await response.json();
    }

    static async addProduct(product) {
        const response = await fetch(x2x, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return await response.json();
    }

    static async updateProduct(id, product) {
        const response = await fetch(`${x2x}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(product)
        });
        return await response.json();
    }

    static async deleteProduct(id) {
        const response = await fetch(`${x2x}/${id}`, { method: 'DELETE' });
        return response;
    }

    static async getById(id) {
        const response = await fetch(`${x2x}/${id}`);
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

        const sortedProducts = products.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
        sortedProducts.forEach(product => UI.addProductToList(product));
    }

    static addProductToList(product) {
        const list = document.getElementById('productList');
        const item = document.createElement('li');
        item.classList.add('product-item');

        item.innerHTML = `
            <div><strong>${product.name}</strong> - ${product.description || ''}</div>
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

    static clearForm() {
        document.getElementById('productForm').reset();
        document.getElementById('productId').value = '';
        grecaptcha.reset(formCaptchaWidgetId);
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

    static confirmDeleteProduct(id) {
        productIdToDelete = id;
        document.getElementById('deleteModal').style.display = 'flex';
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

    document.getElementById('productForm').addEventListener('submit', async (e) => {
        e.preventDefault();

        const captchaToken = grecaptcha.getResponse(formCaptchaWidgetId);

        if (!captchaToken) {
            showToast("⚠️ Verifica que no eres un robot");
            return;
        }

        const id = document.getElementById('productId').value;
        const name = document.getElementById('name').value;
        const description = document.getElementById('description').value;
        const price = parseFloat(document.getElementById('price').value);
        const stock = parseInt(document.getElementById('stock').value);
        const categoryValue = document.getElementById('category').value;

        if (!categoryValue) {
            showToast("Selecciona una categoría válida");
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
            alert('Ocurrió un error al guardar el producto. Revisa la consola.');
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
//capchap para eliminar 
// Variables globales para IDs de los widgets captcha
let formCaptchaWidgetId = null;
let deleteCaptchaWidgetId = null;

// Callback para inicializar los captchas
function onCaptchaLoadCallback() {
    formCaptchaWidgetId = grecaptcha.render('formCaptcha', {
        'sitekey': '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });

    deleteCaptchaWidgetId = grecaptcha.render('deleteCaptcha', {
        'sitekey': '6LeBDyIrAAAAAHdwjAS0twFgUkp3gJgnWn3qDfW-'
    });
}
document.addEventListener('contextmenu', function(e) {
    e.preventDefault();  // Deshabilita el clic derecho
  });
  
  document.addEventListener('keydown', function(e) {
    // Bloquear combinaciones de teclas como F12 y Ctrl+Shift+I
    if (e.key === "F12" || (e.ctrlKey && e.shiftKey && (e.key === "I" || e.key === "C"))) {
      e.preventDefault();
    }
  });

  //detecta q usen la consola
  (function () {
    let isDevToolsOpen = false;
    let alreadyBlocked = false;
  
    const detectDevTools = function () {
      const start = performance.now();
      debugger; // Esto detiene el hilo si la consola está abierta
      const end = performance.now();
      if (end - start > 100) {
        isDevToolsOpen = true;
      }
    };
  
    const blockAllRequests = function () {
      if (alreadyBlocked) return;
      alreadyBlocked = true;
  
      // Bloqueo de fetch
      window.fetch = function () {
        console.warn("🔒 Seguridad: fetch bloqueado.");
        return Promise.reject(new Error("fetch bloqueado por seguridad"));
      };
  
      // Bloqueo de XMLHttpRequest
      const BlockedXHR = function () {
        throw new Error("🔒 Seguridad: XMLHttpRequest bloqueado.");
      };
      window.XMLHttpRequest = BlockedXHR;
  
      // Bloqueo de WebSocket
      window.WebSocket = function () {
        throw new Error("🔒 Seguridad: WebSocket bloqueado.");
      };
  
      // Mensaje de advertencia en la pantalla
      const warning = document.createElement("div");
      warning.style.position = "fixed";
      warning.style.top = "0";
      warning.style.left = "0";
      warning.style.width = "100%";
      warning.style.height = "100%";
      warning.style.backgroundColor = "#000000ee";
      warning.style.color = "#ff4f4f";
      warning.style.display = "flex";
      warning.style.flexDirection = "column";
      warning.style.justifyContent = "center";
      warning.style.alignItems = "center";
      warning.style.fontSize = "2rem";
      warning.style.zIndex = "999999";
      warning.innerHTML = `
        <strong>⚠️ Seguridad activada</strong>
        <p>El uso de herramientas de desarrollador está restringido.</p>
        <p>Las conexiones han sido bloqueadas.</p>
      `;
      document.body.appendChild(warning);
    };
  
    // Revisión periódica
    setInterval(() => {
      detectDevTools();
      if (isDevToolsOpen) {
        console.warn("🚫 DevTools detectado. Bloqueando...");
        blockAllRequests();
      }
    }, 1000);
  })();
  

