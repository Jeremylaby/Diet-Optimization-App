export enum HttpMethod {
  GET = 'get',
  POST = 'post',
  PUT = 'put',
  DELETE = 'delete',
}
export type LoginRequest = {
  username: string;
  password: string;
};

export type LoginResponse = {
  message: string;
};

export type RegisterRequest = {
  username: string;
  email: string;
  password: string;
};

export type LogoutResponse = {
  message: string;
};

export enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE',
  OTHER = 'OTHER',
}

export enum Role {
  USER = 'USER',
  ADMIN = 'ADMIN',
}

export type User = {
  id: number;
  username: string;
  email: string;
  age: number | null;
  gender: Gender | null;
  height: number | null;
  weight: number | null;
  energyReq: number | null;
  proteinReq: number | null;
  carbsReq: number | null;
  fatReq: number | null;
  role: Role;
};
