interface PostModel {
  id: number;  
  taste: number;
  clean: number;
  service: number;
  content: string;
  entryDate: string;
  modifyDate: string;
  averageRating: number;
  tags: string[]; 
  images: ImageModel[];
}

