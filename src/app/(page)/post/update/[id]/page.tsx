"use client";
import Star from '@/app/(page)/star/page';
import { ST } from 'next/dist/shared/lib/utils';
import { useParams, useRouter } from 'next/navigation';
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'

export default function PostUpdate() {
  const router = useRouter();
  const { id } = useParams();
  const [allTags, setAllTags] = useState<{[category: string]: TagModel[]}>({});
  const [selectTags, setSelectTags] = useState<string[]>([]);
  const [selectImages, setSelectImages] = useState<File[]>([]);
  const [prevImages, setPrevImages] = useState<ImageModel[]>([]);
  const [imagesToDelete, setImagesToDelete] = useState<string[]>([]); 

  const [formData, setFormData] = useState<PostModel>({
    id: 0,
    taste: 0,
    clean: 0,
    service: 0,
    content: '',
    entryDate: '',
    modifyDate: '',
    averageRating: 0,
    tags: [],
    images: []
  });

  useEffect(() => {
    const fetchPost = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/posts/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch post');
        }
        const data: PostModel = await response.json();
        console.log('Fetched post data:', data);
        setFormData(data);
        setSelectTags(data.tags);
        setPrevImages(data.images);
        console.log('Images in Post:', data.images);  // 로그 추가
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    const fetchTags = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/tags/category');
        if (!response.ok) {
          throw new Error('Failed to fetch tags');
        }
        const data = await response.json();
        setAllTags(data);
      } catch (error) {
        console.error('Error fetching tags:', error);
      }
    };

    if (id) {
      fetchPost();
    }
    fetchTags();
  }, [id]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const updatePost = {
        ...formData,
        id: formData.id,   
        tags: selectTags,
      };

      const response = await fetch(`http://localhost:8080/api/posts/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatePost)
      });

      if (!response.ok) {
        throw new Error('Failed to update post');
      }

      if (imagesToDelete.length > 0) {
        for (const imageId of imagesToDelete){
          await fetch (`http://localhost:8080/api/images/${imageId}`,{
            method:'DELETE',
          });
        }
      }

      if(selectImages.length > 0){
        const imageData = new FormData();
        selectImages.forEach((file) => {
          imageData.append('files', file);
        });
        imageData.append('postId', String(id));

        const imageResponse = await fetch(`http://localhost:8080/api/images`,{
          method: 'POST',
          body: imageData,
        });

        if(!imageResponse.ok){
          throw new Error('Failed to upload images');
        }
      }

      router.push(`/post/details/${id}`);
    } catch (error) {
      console.error('Error updating post:', error);
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleTagSelect = (tag: string) => {
    setSelectTags((prevSelected) =>
      prevSelected.includes(tag)
        ? prevSelected.filter((t) => t !== tag)
        : [...prevSelected, tag]
    );
  };

  const handleStar = (value: number, field: keyof PostModel) => {
    setFormData((prevData) => ({
      ...prevData,
      [field]: value
    }));
  };

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setSelectImages(Array.from(e.target.files));
    }
  };

  const handleDeleteImage = (imageId: string) => {
    console.log("Image ID in handleDeleteImage:", imageId);
    if (imageId === null || imageId === undefined) {
      console.error('Invalid image ID:', imageId);
      return;
    }
  
    setImagesToDelete((prev) => [...prev, imageId]);
    setPrevImages((prevImages) => prevImages.filter(img => img.id !== imageId));
  };

  return (
    <main className="flex min-h-screen flex-col items-center">
      <h1 className="text-2xl font-bold mb-6">[게시글 수정]</h1>
      <form onSubmit={handleSubmit} className="w-full max-w-2xl" encType="multipart/form-data">
        <div>
          <h2 className="font-bold">[항목별 평점]</h2>
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">맛</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.taste}
            onChange={(rating) => handleStar(rating, "taste")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">청결</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.clean}
            onChange={(rating) => handleStar(rating, "clean")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">서비스</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData.service}
            onChange={(rating) => handleStar(rating, "service")}
          />
        </div>
        <div>
          <h2 className="font-bold">[음식점 키워드]</h2>
          {Object.keys(allTags).map(category => (
            <div key={category} className="mb-4">
              <h3>{category}</h3>
              <div className="flex flex-wrap gap-4">
                {(allTags[category] as TagModel[]).map(t => (
                  <div key={t.name} className="flex items-center">
                    <input
                      type="checkbox"
                      id={t.name}
                      name={t.name}
                      checked={selectTags.includes(t.name)}
                      onChange={() => handleTagSelect(t.name)}
                    />
                    <label htmlFor={t.name} className="ml-2">{t.name}</label>
                  </div>
                ))}
              </div>
            </div>
          ))}
        </div>
        <div>
          <label className="font-bold">[방문후기]</label>
          <textarea
            id="content"
            name="content"
            value={formData.content}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            rows={3}
          />
        </div>

        <div>
          <strong>[이미지]</strong>
          <div className="flex flex-wrap gap-4">
            {prevImages.length > 0 ? (
              prevImages.map((image, index) => (
                <div key={index}>
                  <img 
                     src={`http://localhost:8080/uploads/${image.storedFileName}`}
                     alt={`이미지 ${index + 1}`}
                     style={{ width: '200px', height: 'auto' }}
                  />
                  <button type="button" onClick={() => handleDeleteImage(image.id)} className="bg-transparent hover:bg-red-500 text-red-700 font-semibold hover:text-white py-2 px-4 border border-red-500 hover:border-transparent rounded mr-2">
                    삭제
                  </button>
                </div>
              ))
            ) : (<p>이미지 없음</p>)}
          </div>
        </div>
        <div>
          <input
            type="file"
            multiple
            accept="image/*"
            onChange={uploadImage}
            className="border rounded p-2 w-full"
          />
        </div>

        <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
          수정
        </button>
      </form>
    </main>
  );
}
