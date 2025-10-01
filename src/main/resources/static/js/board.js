// 게시글 목록 (index 페이지에서 사용 - 필요시)
// 현재는 서버에서 렌더링하므로 불필요할 수 있음

// 게시글 작성 폼
document.getElementById('saveForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    try {
        const response = await fetch('/api/boards', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, content })
        });

        const data = await response.json();

        if (response.ok) {
            alert('게시글 작성 성공!');
            window.location.href = '/';
        } else {
            alert(data.msg || '게시글 작성 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});

// 게시글 수정 폼
document.getElementById('updateForm')?.addEventListener('submit', async (e) => {
    e.preventDefault();

    const boardId = document.getElementById('boardId').value;
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;

    try {
        const response = await fetch(`/api/boards/${boardId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ title, content })
        });

        const data = await response.json();

        if (response.ok) {
            alert('게시글 수정 성공!');
            window.location.href = `/board/${boardId}`;
        } else {
            alert(data.msg || '게시글 수정 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});

// 게시글 삭제 버튼
document.getElementById('deleteBtn')?.addEventListener('click', async () => {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    const boardId = document.getElementById('boardId').value;

    try {
        const response = await fetch(`/api/boards/${boardId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        const data = await response.json();

        if (response.ok) {
            alert('게시글 삭제 성공!');
            window.location.href = '/';
        } else {
            alert(data.msg || '게시글 삭제 실패');
        }
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});