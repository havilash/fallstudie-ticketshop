export const BASE_URL = "http://localhost:8080"

export async function login({email, password}) {
    const response = await fetch(`${BASE_URL}/auth/signin`, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({email, password})
    })

    if (response.status === 401) {
        return Promise.reject(await response.json())
    } else if (!response.ok) {
        return Promise.reject(response)
    }

    const data = await response.json()
    return data
}

export async function signup({email, password}) {
    const response = await fetch(`${BASE_URL}/auth/signup`, {
        method: "POST",
        headers: {
            "content-type": "application/json"
        },
        body: JSON.stringify({email, password})
    })

    if (response.status === 409) {
        return Promise.reject(await response.json())
    } else if (response.status >= 400) {
        return Promise.reject(response)
    }

    return await response.json()
}

