"use client";
import axios from "axios";
import React, { useEffect, useState, useRef } from 'react';
import { useRouter } from 'next/navigation';
import {fetchReceiptRegister} from "src/app/service/receipt/receipt.service";

export default function InsertReceipt() {
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [isUploading, setIsUploading] = useState(false);
    const [preview, setPreview] = useState<string | null>(null);
    const router = useRouter();
    const fileInputRef = useRef<HTMLInputElement | null>(null); // ref 생성

    const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            const file = event.target.files[0];
            if (file.size > 10 * 1024 * 1024) { // 10MB
                alert('파일 크기가 10MB를 초과할 수 없습니다.');
                setSelectedFile(null);
                setPreview(null);
                return;
            }
            setSelectedFile(file);
            const fileReader = new FileReader();
            fileReader.onloadend = () => {
                setPreview(fileReader.result as string);
            };
            fileReader.readAsDataURL(file);
        }
    };

    const sendReceipt = async () => {
        if (selectedFile && !isUploading) {
            setIsUploading(true);
            const formData = new FormData();
            formData.append('file', selectedFile);

            try {
                const resp = await fetchReceiptRegister(formData);

                if (resp.status === 200) {

                    console.log(resp);
                    const restaurantId = resp.data.id;

                    if (restaurantId == null) {
                        alert("이미 등록된 정보입니다");
                        router.push(`/`);
                    } else {
                        router.push(`/receipt/receiptRestaurant/${restaurantId}`);
                    }
                }

            } catch (error) {
                console.error('파일 업로드 오류:', error);
            } finally {
                setIsUploading(false);
            }
        }
    };

    const handleImageClick = () => {
        if (fileInputRef.current) {
            fileInputRef.current.click();
        }
    };

    return (
        <div style={{marginTop: '20px', display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <img
                src="/images/save.jpg"
                alt="사진 첨부"
                onClick={handleImageClick}
                style={{cursor: 'pointer'}}
                width={200}
                height={200}
            />
            <input
                ref={fileInputRef}
                className="hidden"
                type="file"
                accept="image/*"
                onChange={handleFileChange}
            />
            {preview && (
                <div>
                    <h3>미리보기:</h3>
                    <img src={preview} alt="미리보기" style={{width: '200px', height: 'auto'}}/>
                </div>
            )}
            <button
                type="submit"
                className="mt-2 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition duration-200" onClick={sendReceipt} disabled={isUploading}
            >사진 첨부</button>
        </div>
);
}