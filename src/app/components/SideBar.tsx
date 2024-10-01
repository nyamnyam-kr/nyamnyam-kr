import React, { useState } from 'react';

interface SectionProps {
  title: string;
  items: string[];
  selectedItems: string[];
  onItemToggle: (item: string) => void;
}

const Section: React.FC<SectionProps> = ({ title, items, selectedItems, onItemToggle }) => {
  return (
    <div className="mb-6">
      <h3 className="text-lg font-semibold mb-2">{title}</h3>
      <div className="grid grid-cols-2 gap-2">
        {items.map((item) => (
          <div key={item} className="flex items-center">
            <input
              type="checkbox"
              checked={selectedItems.includes(item)}
              onChange={() => onItemToggle(item)}
              className="mr-2"
            />
            <span className="text-gray-700">{item}</span>
          </div>
        ))}
      </div>
    </div>
  );
};

const Sidebar: React.FC<{ onFilterChange: (filters: string[], categories: string[]) => void }> = ({ onFilterChange }) => {
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  const [selectedCategories, setSelectedCategories] = useState<string[]>([]);

  const handleTagToggle = (item: string) => {
    setSelectedTags((prev) => {
      const isSelected = prev.includes(item);
      const updatedTags = isSelected
        ? prev.filter((tag) => tag !== item)
        : [...prev, item];

      onFilterChange(updatedTags, selectedCategories); 
      return updatedTags;
    });
  };

  const handleCategoryToggle = (item: string) => {
    setSelectedCategories((prev) => {
      const isSelected = prev.includes(item);
      const updatedCategories = isSelected
        ? prev.filter((category) => category !== item)
        : [...prev, item];

      onFilterChange(selectedTags, updatedCategories); 
      return updatedCategories;
    });
  };

  return (

    <div className="w-64 pt-10 bg-gray-100 h-screen fixed top-500 left-0 shadow-lg rounded-lg overflow-y-auto">
      <Section
        title="이용자층"
        items={['20대', '30대', '40대', '50대', '60대 이상']}
        selectedItems={selectedTags}
        onItemToggle={handleTagToggle}
      />
      <Section
        title="카테고리"
        items={['한식', '일식', '중식', '양식', '아시아', '해산물', '술집', '카페']}
        selectedItems={selectedCategories}
        onItemToggle={handleCategoryToggle}
      />
        <Section
        title="방문목적"
        items={['가족 모임', '관광', '기념일', '데이트', '출장', '친구 모임', '특별 이벤트', '혼밥', '회식', '휴식']}
        selectedItems={selectedTags}
        onItemToggle={handleTagToggle}
      />
      <Section
        title="편의시설"
        items={['드라이브 스루', '무료 Wi-Fi', '바 테이블', '배달 서비스', '아동 시설', '애완동물 출입 가능', '예약 가능', '주차장', '테라스 좌석', '휠체어 접근 가능']}
        selectedItems={selectedTags}
        onItemToggle={handleTagToggle}
      />
      <Section
        title="분위기"
        items={['고급스러움', '로맨틱', '소박함', '조용함', '아늑함', '유니크함', '전통적', '캐주얼', '현대적', '활기참']}
        selectedItems={selectedTags}
        onItemToggle={handleTagToggle}
      />
    </div>
  );
};

export default Sidebar;