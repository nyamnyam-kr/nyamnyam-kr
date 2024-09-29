import { tagCategoryApi } from "src/app/api/tag/tag.api";
import { TagModel } from "src/app/model/tag.model";

export async function insertTag(tag: TagModel): Promise<any | { status: number }> {
    try {
        const body = {
            name: tag.name,
            tagCategory: tag.tagCategory
        }
        const response = await fetch('http://localhost:8080/api/tags', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body),
        });

        const contentType =
            response.headers.get('content-type');

        if (response.ok && contentType?.includes('application.json')) {
            const data: any = await response.json();
            return data;
        } else {
            const errorMessage = await response.text();
            throw new Error(`Server returned non-JSON response: ${errorMessage}`);
        }
    } catch (error) {
        console.error('Error occurred while inserting tag:', error);
        return { status: 500 };
    }
}

export const fetchTagData = async (): Promise<{ [category: string]: TagModel[] }> => {
    try {
        const tags = await tagCategoryApi();
        console.log("fetchTagData: ", tags);
        const categorizedTags = tags.reduce((acc: { [category: string]: TagModel[] }, tag: TagModel) => {
            const category = tag.tagCategory || '기타';
            if (!acc[category]) acc[category] = [];
            acc[category].push(tag);
            return acc;
        }, {});
        console.log("Categorized Tags:", categorizedTags);
        return categorizedTags;
    } catch (error) {
        console.error("Error in fetchTagsData:", error);
        throw error;
    }
};
