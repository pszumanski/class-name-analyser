import SockJS from "sockjs-client/dist/sockjs";
import * as Stomp from 'stompjs';
import { ApiResponse } from '../types/ApiResponse';

const backendUrl = import.meta.env.VITE_BACKEND_URL;

export class ApiService {

    private data: ApiResponse;
    private isInitalized: boolean;
    private sessionId: string;

    constructor() {
        this.data = {
            language: "Java",
            totalRepositoriesCount: 0,
            repositoriesAnalysed: 0,
            classesAnalysed: 0,
            validClasses: 0,
            wordsAnalysed: 0,
            averageWordsPerClass: 0.0,
            percentageOfValidClasses: 0.0,
            words: new Map<string, number>()
        } as ApiResponse;
        this.sessionId = crypto.randomUUID();
        this.isInitalized = false;
    }

    public init(self: ApiService, language: string): void {
        const webSocket = new SockJS(`${backendUrl}/websocket`);
        const stompClient = Stomp.over(webSocket);

        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);

            stompClient.subscribe(`/topic/${self.sessionId}/update`, (message) => {
                self.data = JSON.parse(message.body) as ApiResponse;
                self.isInitalized = true;
            });
            
            stompClient.send('/app/init', {}, JSON.stringify(
                {
                    language: language,
                    sessionId: self.sessionId
                }
            ));
            
        });
    }

    public getApiData(): ApiResponse {
        return this.data;
    }

    public isInitialized(): boolean {
        return this.isInitalized;
    }
}