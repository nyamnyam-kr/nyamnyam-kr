"use client";
import React, { useState } from 'react';
import { insertImage } from '@/app/service/image/image.api';

export default function ImageRegisterPage() {
    const [selectedFiles, setSelectedFiles] = useState<FileList | null>(null);
    const [postContent, setPostContent] = useState('');

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            setSelectedFiles(event.target.files);
        }
    };

    const getFileExtension = (filename: string) => {
        return filename.slice(((filename.lastIndexOf(".") - 1) >>> 0) + 2);
    };

    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        if (selectedFiles && selectedFiles.length > 0) {
            for (let i = 0; i < selectedFiles.length; i++) {
                const file = selectedFiles[i];
                const originalFilename = file.name;
                const storedFileName = Date.now() + '-' + originalFilename;
                const extension = getFileExtension(originalFilename);

                const image: ImageModel = {
                    id: 0,
                    originalFilename,
                    storedFileName,
                    extension
                };

                try {
                    const response = await insertImage(image);
                    if (response.status === 200) {
                        alert('이미지 업로드 성공');
                    } else {
                        alert('이미지 업로드 실패');
                    }
                } catch (error) {
                    alert('이미지 업로드 오류');
                }
            }
        } else {
            alert('파일이 선택되지 않았습니다.');
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
}
