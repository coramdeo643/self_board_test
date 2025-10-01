// 회원가입 폼
document.getElementById('joinForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    try {
        const response = await fetch('/api/users/join', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password, email })
        });

        const data = await response.json();

        if (response.ok) {
            alert('회원가입 성공!');
            window.location.href = '/users/login-form';
        } else {
            alert(data.msg || '회원가입 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});

// 로그인 폼
document.getElementById('loginForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (response.ok) {
            // JWT 토큰은 헤더에서 자동 처리됨
            alert('로그인 성공!');
            window.location.href = '/';
        } else {
            alert(data.msg || '로그인 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});

// 회원정보 수정 폼
document.getElementById('updateForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;

    try {
        const response = await fetch(`/api/users/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ password, email })
        });

        const data = await response.json();

        if (response.ok) {
            alert('수정 성공!');
            window.location.href = '/';
        } else {
            alert(data.msg || '수정 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});

// 로그아웃
document.getElementById('logoutBtn')?.addEventListener('click', async (e) => {
    e.preventDefault();

    try {
        const response = await fetch('/api/users/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.ok) {
            alert('로그아웃 되었습니다.');
            window.location.href = '/';
        } else {
            alert('로그아웃 실패');
        }
    } catch (error) {
        console.error(error);
        alert('서버 오류가 발생했습니다.');
    }
});