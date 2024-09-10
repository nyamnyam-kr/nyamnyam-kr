import React, { useState } from 'react';
import axios from 'axios';

const ImageRegisterPage = () => {
    const [selectedFiles, setSelectedFiles] = useState<FileList | null>(null); // 선택된 파일 상태
    const [postContent, setPostContent] = useState(''); // 게시글 내용 상태

    // 파일 선택 핸들러
    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            setSelectedFiles(event.target.files);
        }
    };

    // 폼 제출 핸들러
    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
    
        const formData = new FormData();
    
        // 게시글 내용 추가
        formData.append('post', new Blob([JSON.stringify({
            content: postContent, // 게시글 내용 추가
        })], { type: 'application/json' }));
    
        // 선택된 파일이 있는지 확인하고 처리
        if (selectedFiles && selectedFiles.length > 0) {
            Array.from(selectedFiles).forEach((file) => {
                formData.append('files', file); // 파일을 formData에 추가
            });
        } else {
            alert('파일이 선택되지 않았습니다.');
            return; // 파일이 없을 경우 제출을 중지
        }
    
        try {
            const response = await axios.post('http://localhost:8080/api/posts', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            if (response.status === 200) {
                alert('파일 업로드 성공');
            }
        } catch (error) {
            console.error('파일 업로드 실패:', error);
            alert('파일 업로드 실패');
        }
    };

    return (
        <div>
            <h2>첨부파일 등록</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>게시글 내용:</label>
                    <textarea
                        value={postContent}
                        onChange={(e) => setPostContent(e.target.value)}
                    />
                </div>
                <div>
                    <label>이미지 파일:</label>
                    <input type="file" multiple onChange={handleFileChange} />
                </div>
                <button type="submit">등록하기</button>
            </form>
        </div>
    );
};

export default ImageRegisterPage;
