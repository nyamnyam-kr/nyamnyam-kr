// Search.tsx
import React, { useEffect, useState } from 'react';
import { useSearchContext } from './SearchContext';

const Search: React.FC = () => {
  const { searchTerm, setSearchTerm } = useSearchContext(); // 검색어와 검색어 설정 함수 가져오기
  const [inputValue, setInputValue] = useState(searchTerm || '');


  // 검색어가 Context에서 변경될 때 로컬 상태를 업데이트하여 동기화
  useEffect(() => {
    setInputValue(searchTerm || '');
  }, [searchTerm]);

  // 입력 필드의 변화 감지 및 로컬 상태 업데이트
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value); // 로컬 상태 업데이트
  };

  // 검색어 제출 시 Context의 searchTerm 업데이트
  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSearchTerm(inputValue); // Context의 searchTerm 업데이트
    window.history.pushState({}, '', `?search=${inputValue}`);
  };


  return (
    <form onSubmit={handleSearchSubmit} className="search">
      <div className="search__input">
        <i className="ico_search"></i>
        <input
          type="search"
          name="search"
          placeholder="Search"
          value={inputValue} // inputValue로 input 필드 상태 관리
          onChange={handleSearchChange} // 변화 감지 핸들러
        />
      </div>
      <div className="search__btn">
        <button type="submit"> {/* 버튼 클릭 시 handleSearchSubmit 실행 */}
          <i className="ico_microphone"></i>
        </button>
      </div>
    </form>
  );
};

export default Search;