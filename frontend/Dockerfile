FROM node:18-alpine

WORKDIR /app

COPY package.json .

RUN npm install

COPY . .

ARG BACKEND_URL

RUN echo VITE_BACKEND_URL=$BACKEND_URL > .env

RUN npm run build

EXPOSE 5175

CMD [ "npm", "run", "preview", "--", "--host", "0.0.0.0" ]