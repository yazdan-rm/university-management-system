import type { KcContext } from "./login/KcContext.ts";

type PageProps<T> = {
    kcContext: Extract<KcContext, {pageId: T}>
}

export type {PageProps};