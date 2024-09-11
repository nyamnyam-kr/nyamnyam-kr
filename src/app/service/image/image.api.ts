export async function insertImage(img: ImageModel): Promise<any | {status: number}> {
  try {
    const body = {
      id: img.id,
      originalFilename: img.originalFilename,
      storedFileName : img.storedFileName,
      extension: img.extension  
    }
    const response = await fetch('http://localhost:8080/api/images', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });

    const contentType = response.headers.get('content-type');

    if (response.ok && contentType?.includes('application/json')) {
      const data: any = await response.json();
      return data;
    } else {
      const errorMessage = await response.text();
      throw new Error(`Server returned non-JSON response: ${errorMessage}`);
    }
  } catch (error) {
    console.error('Error occurred while inserting post:', error);
    return {status: 500};
  }
}