import jwtDecode from "jwt-decode"
import {useRouter} from "next/router"
import {useEffect, useState} from "react"

const STORAGE_KEY = "session"

const defaultModel = {
    user: null,
    token: null,

}

export default function useSession() {
    const [session, setSession] = useState(defaultModel)
    const [ready, setReady] = useState(false)

    useEffect(() => {
        const savedSession = localStorage.getItem(STORAGE_KEY)
        if (savedSession) {
            try {
                const value = JSON.parse(savedSession)
                const {exp} = jwtDecode(value.token)
                const expirationDate = new Date(0)
                expirationDate.setUTCSeconds(exp)
                const now = new Date()
                setSession(now >= expirationDate ? defaultModel : value)
            } catch (e) {
                console.log(e)
            }
        }
        setReady(true)
    }, [])

    useEffect(() => {
        if (session.user) {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(session))
        } else {
            localStorage.removeItem(STORAGE_KEY)
        }
    }, [session])

    return {
        ...session,
        ready,
        signIn({id, authorities, accessToken}) {
            const user = {
                scopes: authorities.map(a => a.authority),
                id,
            }

            setSession({
                user, token: accessToken
            })
        },
        logOut() {
            setSession(defaultModel)
        }
    }
}

export function useRedirectToLogin(session) {
    const router = useRouter()

    useEffect(() => {
        if (session.ready && !session.user) router.push("/login")
    }, [session, router])
}

export function useRedirectToHome(session) {
    const router = useRouter()

    useEffect(() => {
        if (session.ready && session.user) router.push("/")
    }, [session, router])
}

export function checkHasAnyOfScopes(scopes, session) {
    return !!session?.user?.scopes?.some(scope => scopes.includes(scope));
}

export function useRedirectIfNotAllowed(scopes, session, path) {
    const router = useRouter()

    useEffect(() => {


        if (session.ready && !checkHasAnyOfScopes(scopes, session)) router.push(path ? path : "/notAllowed")
    }, [session, router])
}
