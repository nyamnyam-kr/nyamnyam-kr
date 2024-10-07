"use client";

import { useParams, useRouter } from 'next/navigation';
import { ChangeEvent, FormEvent, useEffect, useState } from 'react'
import Star from 'src/app/components/Star';
import { initialPost, PostModel } from 'src/app/model/post.model';
import { TagModel } from 'src/app/model/tag.model';
import {postService} from 'src/app/service/post/post.service';
import { tagService } from 'src/app/service/tag/tag.service';

export default function PostUpdate() {
  const router = useRouter();
  const { restaurantId, id } = useParams();
  const [allTags, setAllTags] = useState<{ [category: string]: TagModel[] }>({});
  const [tags, setTags] = useState<string[]>([]);
  const [images, setImages] = useState<File[]>([]);
  const [prevImages, setPrevImages] = useState<ImageModel[]>([]);
  const [imagesToDelete, setImagesToDelete] = useState<number[]>([]);
  const [formData, setFormData] = useState<PostModel>(initialPost);

  useEffect(() => {
    console.log("useEffect 내부 ID: ", id, "타입: ", typeof id);
    if (typeof id === 'string' && id) {
      console.log("유효한 ID: ", id);
      loadData(Number(id));
    } else {
      console.error("invalid ID: ", id);
    }
  }, [id]);

  useEffect(()=> {
    console.log("allTags 상태 변경되었습니다.: ", allTags); 
  },[allTags]);

  const loadData = async (id: number) => {
    console.log("loadData 함수가 호출되었습니다. Id: ", id);
    try {
      const post = await postService.getPostDetails(id);
      console.log("post 데이터: ", post);

      const uniqueTags = Array.isArray(post.tags) ? Array.from(new Set(post.tags)) : [];

      setFormData({ ...post, tags: uniqueTags });
      setTags(uniqueTags);
      setPrevImages(post.images || []);

      const tagList = await tagService.fetchTag();
      console.log("TagList: ", tagList)
      setAllTags(tagList);

    } catch (error) {
      console.error("Error loading data:", error);
    }
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const uniqueTags = Array.from(new Set(tags));
      const updatePost = {
        ...formData,
        id: formData.id,
        tags: uniqueTags,
      };

      await postService.update(Number(id), updatePost, images, imagesToDelete);
      router.push(`/post/${restaurantId}/details/${id}`);

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
    console.log("Tag Selected: ", tag);
    setTags((prevSelected) => {
      const tagSet = new Set(prevSelected);
      if (tagSet.has(tag)) {
        tagSet.delete(tag);
      } else {
        tagSet.add(tag);
      }
      return Array.from(tagSet);
    });
  };

  const handleStar = (value: number, field: keyof PostModel) => {
    setFormData((prevData) => ({
      ...prevData,
      [field]: value
    }));
  };

  const uploadImage = (e: ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setImages(Array.from(e.target.files));
    }
  };

  const handleDeleteImage = (imageId: number) => {
    if (!imageId) {
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
            rate={formData?.taste}
            onChange={(rating) => handleStar(rating, "taste")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">청결</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData?.clean}
            onChange={(rating) => handleStar(rating, "clean")}
          />
        </div>
        <div className="flex items-center mb-4">
          <label className="block font-bold w-24">서비스</label>
          <Star
            w="w-8" h="h-8"
            readonly={false}
            rate={formData?.service}
            onChange={(rating) => handleStar(rating, "service")}
          />
        </div>
        <div>
          <h2 className="font-bold">[음식점 키워드]</h2>
          {Object.keys(allTags).length > 0 ? (
            Object.keys(allTags).map(category => (
              <div key={category} className="mb-4">
                <h3>{category}</h3>
                <div className="flex flex-wrap gap-4">
                  {(allTags[category] as TagModel[]).map(t => (
                    <div key={t.name} className="flex items-center">
                      <input
                        type="checkbox"
                        id={t.name}
                        name={t.name}
                        checked={tags.includes(t.name)}
                        onChange={() => handleTagSelect(t.name)}
                      />
                      <label htmlFor={t.name} className="ml-2">{t.name}</label>
                    </div>
                  ))}
                </div>
              </div>
            ))
          ) : (
            <p>태그가 없습니다.</p>
          )}
        </div>
        <div>
          <label className="font-bold">[방문후기]</label>
          <textarea
            id="content"
            name="content"
            value={formData?.content}
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