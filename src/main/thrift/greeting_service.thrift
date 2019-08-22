namespace * com.taubler.greet.thrift

struct FullName {
  1: string firstName,
  2: string lastName,
  3: string suffix
}

service GreetingService {
  string beGreeted(1: FullName name)
  string beUngreeted(1: FullName name, 2: string newLocation)
}