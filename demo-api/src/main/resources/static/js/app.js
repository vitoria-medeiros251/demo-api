async function cadastrar() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const mensagem = document.getElementById('mensagem');

    if (!username || !password) {
        mensagem.style.color = 'red';
        mensagem.textContent = 'Preencha todos os campos.';
        return;
    }

    if (password.length !== 6) {
        mensagem.style.color = 'red';
        mensagem.textContent = 'A senha deve ter exatamente 6 caracteres.';
        return;
    }

    try {
        const response = await fetch('/api/v1/users', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        });

        if (response.status === 201) {
            mensagem.style.color = 'green';
            mensagem.textContent = 'Usuário cadastrado com sucesso!';
        } else if (response.status === 409) {
            mensagem.style.color = 'red';
            mensagem.textContent = 'E-mail já cadastrado.';
        } else {
            const erro = await response.json();
            mensagem.style.color = 'red';
            mensagem.textContent = erro.message || 'Dados inválidos.';
        }
    } catch (e) {
        mensagem.style.color = 'red';
        mensagem.textContent = 'Erro ao conectar com o servidor.';
    }
}
